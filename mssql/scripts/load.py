import time
import sys
import os
from SQLServerSNBLoader import SQLServerSNBLoader
from DBLoader import DBLoader

def load_sql_graph_data(DBL):
    snb_loader = SQLServerSNBLoader('/data/dynamic/', DBL)
    print("Load person table")
    start = time.time()
    snb_loader.insert_persons_sql_graph()
    end = time.time()
    duration = end - start
    print(f"-> {duration:.4f} seconds")

    print("Load Knows table")
    start = time.time()
    snb_loader.insert_knows_sql_graph()
    end = time.time()
    duration = end - start
    print(f"-> {duration:.4f} seconds")
    return

if __name__ == "__main__":
    # Fetch the env variables.
    DB_PORT = os.getenv("MSSQL_PORT", 1433)
    DB_NAME = os.getenv("MSSQL_DB_NAME", "ldbc")
    DB_USER = os.getenv("MSSQL_USER", "SA")
    DB_PASS = os.getenv("MSSQL_PASSWORD", "")
    RECREATE = os.getenv("MSSQL_RECREATE", True)
    SERVER_NAME = os.getenv("MSSQL_SERVER_NAME", "snb-interactive-mssql")
    DB_DRIVER = os.getenv("MSSQL_DRIVER", "ODBC Driver 18 for SQL Server")

    DBL = DBLoader(SERVER_NAME, DB_NAME, DB_USER, DB_PASS, DB_PORT, DB_DRIVER)
    DBL.check_and_create_database(DB_NAME, RECREATE)

    print("Create tables")
    DBL.run_ddl_scripts("ddl/schema.sql")

    print("Load data for SQL Graph")
    load_sql_graph_data(DBL)

    print("Load initial snapshot")
    DBL.run_ddl_scripts("ddl/load.sql")

    print("Creating materialized views . . . ")
    DBL.run_ddl_scripts("ddl/schema_constraints.sql")

    try:
        print("Create user")
        DBL.run_ddl_scripts("ddl/create_user.sql")
    except Exception as e:
        print("Error creating user:", e, file=sys.stderr, )

    print("Loaded initial snapshot to SQL Server.")
