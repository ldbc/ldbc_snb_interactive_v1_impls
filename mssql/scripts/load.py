import time
import sys
import os
from SQLServerSNBLoader import SQLServerSNBLoader
from DBLoader import DBLoader

def __execute_load(func, table_name):
    print(f"Load {table_name} table")
    start = time.time()
    func()
    end = time.time()
    duration = end - start
    print(f"-> {duration:.4f} seconds")

def load_sql_graph_data(DBL):
    snb_loader = SQLServerSNBLoader('/data/', DBL)
    __execute_load(snb_loader.insert_organisation_to_sql, "organisation")
    __execute_load(snb_loader.insert_persons_sql_graph, "person")
    __execute_load(snb_loader.insert_likes_to_sql, "likes")
    __execute_load(snb_loader.insert_forum_person_to_sql, "forum_person")
    __execute_load(snb_loader.insert_forum_to_sql, "forum")
    __execute_load(snb_loader.insert_tag_to_sql, "tag")
    __execute_load(snb_loader.insert_tagclass_to_sql, "tagclass")
    __execute_load(snb_loader.insert_places_to_sql, "places")
    __execute_load(snb_loader.insert_comment_to_sql, "comment")
    __execute_load(snb_loader.insert_post_to_sql, "post")
    __execute_load(snb_loader.insert_knows_sql_graph, "knows")
    return

if __name__ == "__main__":
    # Fetch the env variables.
    start_total = time.time()
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
    end_total = time.time()
    duration_total = end_total - start_total
    print("Loaded initial snapshot to SQL Server.")
    print(f"-> {duration_total:.4f} seconds")
