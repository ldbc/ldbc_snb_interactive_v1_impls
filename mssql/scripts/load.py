"""
FILE: load.py
DESC: This file loads the LDBC SNB Spark dataset to MS SQL Server.
      To automate the loading of the files, the ddl/load.sql file
      contains replacement markers to insert the path to the csv
      file(s) in the BULK INSERT statement. This step is required
      since the Spark datagen outputs a different filename each time
      it is run. The entity_to_folder dictionary contains the mapping
      of the replacement marker to the folder where the part-0000X
      files are located
"""
import time
import re
import os
import glob
from DBLoader import DBLoader

entity_to_folder = {
    ":organisation_csv" : "/data/static/Organisation",
    ":place_csv" : "/data/static/Place",
    ":tag_csv" : "/data/static/Tag",
    ":tagclass_csv" : "/data/static/TagClass",
    ":comment_csv" : "/data/dynamic/Comment",
    ":comment_hastag_tag_csv" :"/data/dynamic/Comment_hasTag_Tag",
    ":forum_csv" :"/data/dynamic/Forum",
    ":forum_hasmember_person_csv" :"/data/dynamic/Forum_hasMember_Person",
    ":forum_hastag_tag_csv" :"/data/dynamic/Forum_hasTag_Tag",
    ":person_csv" :"/data/dynamic/Person",
    ":person_hasinterest_tag_csv" :"/data/dynamic/Person_hasInterest_Tag",
    ":person_knows_person_csv" :"/data/dynamic/Person_knows_Person",
    ":person_likes_comment_csv" : "/data/dynamic/Person_likes_Comment",
    ":person_likes_post_csv" : "/data/dynamic/Person_likes_Post",
    ":person_studyat_university_csv" : "/data/dynamic/Person_studyAt_University",
    ":person_workat_company_csv" : "/data/dynamic/Person_workAt_Company",
    ":post_csv" : "/data/dynamic/Post",
    ":post_hastag_tag_csv" : "/data/dynamic/Post_hasTag_Tag"
}

def load_data(path_to_file, DBL):
    """
    Function to load the data.
    It reads each line in the load.sql and replaces the csv-file.
    It stores the result in a temp_load.sql, which is removed
    after loading is completed
    Args:
    - path_to_file: Path to the load.sql file (usually ddl/load.sql)
    - DBL: The DatabaseLoader object
    """
    with open(path_to_file, "r") as f:
        with open('ddl/load_temp.sql', "w") as w:
            queries_file = f.read()
            queries = queries_file.split(";")
            for query in queries:
                if query.isspace():
                    continue
                print(query)
                m = re.search(':.*csv', query)
                if(m):
                    csv_key = m.group(0)
                    csv_files = glob.glob(f'{entity_to_folder[csv_key]}/*.csv', recursive=True)
                    for csv_path in csv_files:
                        query_new = query.replace(csv_key, csv_path)
                        w.write(query_new + ';')
                else:
                    w.write(query + ';')
            w.write('\n')
    DBL.run_ddl_scripts("ddl/load_temp.sql")
    os.remove('ddl/load_temp.sql')


if __name__ == "__main__":
    # Fetch the env variables.
    DB_PORT = os.getenv("MSSQL_PORT", 1433)
    DB_NAME = os.getenv("MSSQL_DB_NAME", "ldbc")
    DB_USER = os.getenv("MSSQL_USER", "SA")
    DB_PASS = os.getenv("MSSQL_PASSWORD", "")
    RECREATE = os.getenv("MSSQL_RECREATE", True)
    EXTERNAL_AZURE = os.getenv("MSSQL_EXTERNAL_AZURE", False)
    SERVER_NAME = os.getenv("MSSQL_SERVER_NAME", "snb-interactive-mssql")
    DB_DRIVER = os.getenv("MSSQL_DRIVER", "ODBC Driver 18 for SQL Server")

    start_total = time.time()
    DBL = DBLoader(SERVER_NAME, DB_NAME, DB_USER, DB_PASS, DB_PORT, DB_DRIVER)
    recreated = DBL.check_and_create_database(DB_NAME, RECREATE)

    if (recreated):
        print("Drop existing tables")
        DBL.run_ddl_scripts("ddl/drop-tables.sql")

        print("Create tables")
        DBL.run_ddl_scripts("ddl/schema-composite-merged-fk.sql")
        # TODO: Make sure Azure loader is not executed when variable is set to false
        # This does sometimes happens, which is a bug
        # print(f"Load initial snapshot (External: {EXTERNAL_AZURE})")
        # if EXTERNAL_AZURE:
        #     DBL.run_ddl_scripts("ddl/load-azure-files.sql")
        # else:
        load_data("ddl/load.sql", DBL)

        print("Create static materialized views . . . ")
        DBL.run_ddl_scripts("dml/create-static-materialized-views.sql")

        print("Adding triggers and constraints")
        DBL.run_ddl_scripts("ddl/schema-constraints.sql")
        DBL.run_ddl_scripts("ddl/triggers.sql")
        DBL.run_single_file("ddl/func-calculate-weights.sql")
        DBL.run_single_file("ddl/func-bfs-weight.sql")
        DBL.run_single_file("ddl/func-bfs.sql")
        DBL.run_single_file("ddl/func-distinct-string-agg.sql")
        DBL.run_ddl_scripts("ddl/preprocess.sql")
        print("Done.")

    end_total = time.time()
    duration_total = end_total - start_total
    print("Loaded initial snapshot to SQL Server.")
    print(f"-> {duration_total:.4f} seconds")
