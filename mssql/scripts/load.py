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

print("Load initial snapshot")
run_script(cursor, "ddl/load.sql")

print("Creating materialized views . . . ")
run_script(cursor, "ddl/schema_constraints.sql")

print("Create user ldbc . . . ")
run_script(cursor, "ddl/create_user.sql")
con.commit()
print("Done.")

print("Loaded initial snapshot to SQL Server.")
