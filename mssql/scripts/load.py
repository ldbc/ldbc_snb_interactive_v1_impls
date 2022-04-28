"""
FILE: load.py
DESC: Load LDBC SNB data to SQL Server. 
DEPS: unixodbc-dev (apt)
      pyodbc (pip)
"""

import pyodbc 
import time
import logging
import os
from SQLServerSNBLoader import SQLServerSNBLoader

class DBLoader:

    def __init__(self, server, name, user, password, port, driver):
        """
        Args:
            - server    (str): Server endpoint to connect
            - name      (str): Name of the database
            - user      (str): Username with rights 
            - password  (str): Password of the DB user
            - port      (int): The port to connect
            - driver    (str): Driver pyodbc should use to connect
        """
        self.server = server
        self.name = name
        self.user = user
        self.password = password
        self.port = port
        self.driver = driver

    def get_connection(self):
        return pyodbc.connect('Driver={self.driver};'
                      f'Server={self.server},{self.port};'
                      f'Database=master;'
                      f'uid={self.user};'
                      f'pwd={self.password};'
                      'Trusted_Connection=no;'
                      'encrypt=no;'
                      'sslverify=0;'
                      'Option=3;',
                      autocommit=True)


    def check_and_create_database(self, db_name, recreate=True):
        """
        Args:
            - db_name (str): The name of the database to create.
        """
        con = self.get_connection()

        cursor = con.cursor()
        cursor.execute("SELECT name FROM master.dbo.sysdatabases where name=?;",(db_name,))
        data = cursor.fetchall()

        if not data:
            logging.info(f"Database does not exist. Creating {db_name}.")
            cursor.execute(f"CREATE DATABASE {db_name};")
            logging.info("Database created.")
        else:
            logging.info(f"Database {db_name} already exists. ")
            if recreate:
                logging.info("Recreating database.")
                # This is MS SQL SERVER specific, maybe change to something generic?
                cursor.execute(f"""IF EXISTS (SELECT name from sys.databases WHERE (name = {db_name}))
                            BEGIN
                                ALTER DATABASE {db_name} SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
                                DROP DATABASE {db_name};
                            END;"""
                )
                cursor.execute(f"CREATE DATABASE {db_name};")
                logging.info("Database created.")
        cursor.close()
        con.commit()
        con.close()


    def run_ddl_scripts(self, filename):
        con = self.get_connection()
        with open(filename, "r") as f:
                queries_file = f.read()
                queries = queries_file.split(";")
                for query in queries:
                    if query.isspace():
                        continue

                    print(query)
                    start = time.time()
                    con.execute(query)
                    end = time.time()
                    duration = end - start
                    print(f"-> {duration:.4f} seconds")
        con.commit()
        con.close()


if __name__ == "__main__":
    # Fetch the env variables.
    DB_PORT = os.getenv("MSSQL_PORT", 1433)
    DB_NAME = os.getenv("MSSQL_DB_NAME", "ldbc")
    DB_USER = os.getenv("MSSQL_USER", "")
    DB_PASS = os.getenv("MSSQL_PASSWORD", "")
    SERVER_NAME = os.getenv("MSSQL_SERVER_NAME", "snb-interactive-mssql")

    db_driver = 'ODBC Driver 18 for SQL Server'
    DBL = DBLoader(SERVER_NAME, DB_NAME, DB_USER, DB_PASS, DB_PORT, db_driver)
    DBL.check_and_create_database(DB_NAME)

    logging.info("Create tables")
    DBL.run_ddl_scripts("ddl/schema.sql")

    # This is the alternative loading for SQL Graph

    print("Load person table")
    insert_persons_sql_graph('/data/dynamic/person_0_0.csv')

    print("Load knows table")
    insert_knows_sql_graph('/data/dynamic/person_knows_person_0_0.csv')

    logging.info("Load initial snapshot")
    DBL.run_ddl_scripts("ddl/load.sql")

    logging.info("Creating materialized views . . . ")
    DBL.run_ddl_scripts("ddl/schema_constraints.sql")

    logging.info("Create user")
    DBL.run_ddl_scripts("ddl/create_user.sql")

    logging.info("Loaded initial snapshot to SQL Server.")
    # MARS_Connection=yes
