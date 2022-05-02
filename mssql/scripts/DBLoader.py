import pyodbc 
import time
import logging

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
        return pyodbc.connect(f'Driver={self.driver};'
                      f'Server={self.server},{self.port};'
                      f'Database=master;'
                      f'uid={self.user};'
                      f'pwd={self.password};'
                      'Trusted_Connection=no;'
                      'encrypt=no;'
                      'sslverify=0;'
                      'Option=3;',
                      autocommit=True)

    def check_and_create_database(self, db_name, recreate):
        """
        Args:
            - db_name (str): The name of the database to create.
            - recreate (bool): Whether the database should be recreated.
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
                cursor.execute(f"""
                            BEGIN
                                ALTER DATABASE {db_name} SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
                                DROP DATABASE {db_name};
                            END;"""
                )
                cursor.execute(f"CREATE DATABASE {db_name};")
                logging.info("Database created.")
        cursor.close()
        con.close()

    def run_ddl_scripts(self, path_to_file):
        """
        Run DDL script given the path to the file.
        Args:
            - path_to_file (str): Path to DDL file
        """
        con = self.get_connection()
        with open(path_to_file, "r") as f:
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
        con.close()
