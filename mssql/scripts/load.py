"""
FILE: load.py
DESC: Load LDBC SNB data to SQL Server. 
DEPS: unixodbc-dev (apt)
      pyodbc (pip)
      https://docs.microsoft.com/en-us/sql/connect/odbc/linux-mac/installing-the-microsoft-odbc-driver-for-sql-server?view=sql-server-ver15

"""

import pyodbc 
import time
import sys
import os
from datetime import datetime
import pandas as pd

if len(sys.argv) < 2:
    print("MSSQL loader script")
    print("Usage: load.py <MSSQL_CSV_DIR> [--compressed]")
    exit(1)

DB_PORT = os.getenv("MSSQL_PORT", 1433)
DB_NAME = os.getenv("MSSQL_DB_NAME", "ldbc")
DB_USER = os.getenv("MSSQL_USER", "SA")
DB_PASS = os.getenv("MSSQL_PASSWORD", "")
SERVER_NAME = os.getenv("MSSQL_SERVER_NAME", "snb-interactive-mssql")

data_dir = sys.argv[1]

con = pyodbc.connect('Driver={ODBC Driver 18 for SQL Server};'
                      f'Server={SERVER_NAME},{DB_PORT};'
                      f'Database=master;'
                      f'uid={DB_USER};'
                      f'pwd={DB_PASS};'
                      'Trusted_Connection=no;'
                      'encrypt=no;'
                      'sslverify=0;'
                      'Option=3;',
                      autocommit=True)
cursor = con.cursor()

print("Create database")
cursor.execute("CREATE DATABASE ldbc;")


def insert_person(p_personid, p_firstname, p_lastname, p_gender, p_birthday, p_creationdate, p_locationip, p_browserused, p_placeid):
    """
    """
    stmt = "INSERT INTO dbo.person VALUES (?, ?,?,?,?,?,?,?,?);"
    con.execute(stmt, (p_personid, p_firstname, p_lastname, p_gender, datetime.strptime(p_birthday, '%Y-%m-%d'), datetime.strptime(p_creationdate, '%Y-%m-%dT%H:%M:%S.%f%z'), p_locationip, p_browserused, p_placeid))

def insert_persons_sql_graph(internal_csv_path):
    """
    Inserts all the persons in the SQL Server database in a 
    'node' table.
    Args:
        internal_csv_path (str): 
    """
    chunksize = 10 ** 6
    with pd.read_csv(internal_csv_path, chunksize=chunksize, sep='|') as reader:
        for chunk in reader:
            chunk.apply(lambda row : insert_person(
                row['id'],
                row['firstName'],
                row['lastName'],
                row['gender'],
                row['birthday'],
                row['creationDate'],
                row['locationIP'],
                row['browserUsed'],
                row['place'],
            ), axis = 1)

def insert_knows(person1id, person2id, creationdate):
    # print((person1id, person2id, creationdate))
    stmt = "INSERT INTO dbo.knows VALUES ((SELECT $node_id FROM dbo.person WHERE p_personid = ?),(SELECT $node_id FROM dbo.person WHERE p_personid = ?),?, ?, ?);"
    con.execute(stmt, (person1id, person2id,person1id, person2id, datetime.strptime(creationdate, '%Y-%m-%dT%H:%M:%S.%f%z')))
    con.execute(stmt, (person2id, person1id,person2id, person1id, datetime.strptime(creationdate, '%Y-%m-%dT%H:%M:%S.%f%z')))

def insert_knows_sql_graph(internal_csv_path):
    """
    Inserts all the knows in the SQL Server database in a 
    'edge' table.
    Args:
        internal_csv_path (str): 
    """
    chunksize = 10 ** 6
    with pd.read_csv(internal_csv_path, chunksize=chunksize, sep='|') as reader:
        for chunk in reader:
            chunk.apply(lambda row : insert_knows(
                row[0],
                row[1],
                row[2],
            ), axis = 1)

def run_script(con, filename):
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

print("Create tables")
run_script(cursor, "ddl/schema.sql")

print("Load person table")
insert_persons_sql_graph('/data/dynamic/person_0_0.csv')

print("Load knows table")
insert_knows_sql_graph('/data/dynamic/person_knows_person_0_0.csv')

print("Load initial snapshot")
run_script(cursor, "ddl/load.sql")

print("Creating materialized views . . . ")
run_script(cursor, "ddl/schema_constraints.sql")

print("Create user ldbc . . . ")
run_script(cursor, "ddl/create_user.sql")
con.commit()
print("Done.")

print("Loaded initial snapshot to SQL Server.")
