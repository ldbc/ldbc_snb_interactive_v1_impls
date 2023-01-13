#!/usr/bin/env python3
import glob
from multiprocessing.sharedctypes import Value
import os
import re
import time
import argparse
import psycopg

class UmbraDbLoader():

    def __init__(self):
        self.database = os.environ.get("UMBRA_DATABASE", "ldbcsnb")
        self.endpoint = os.environ.get("UMBRA_HOST", "localhost")
        self.port = int(os.environ.get("UMBRA_PORT", 8000))
        self.user = os.environ.get("UMBRA_USER", "postgres")
        self.password = os.environ.get("UMBRA_PASSWORD", "mysecretpassword")

    def run_script(self, pg_con, cur, filename):
        with open(filename, "r") as f:
            try:
                queries_file = f.read()
                # strip comments
                queries_file = re.sub(r"\n--.*", "", queries_file)
                queries = queries_file.split(";")
                for query in queries:
                    if query.isspace():
                        continue

                    sql_statement = re.findall(r"^((CREATE|INSERT|DROP|DELETE|SELECT|COPY|UPDATE|ALTER) [A-Za-z0-9_ ]*)", query, re.MULTILINE)
                    is_update = True if re.match(r"^((CREATE|INSERT|DROP|DELETE|COPY|UPDATE|ALTER) [A-Za-z0-9_ ]*)", sql_statement[0][0], re.MULTILINE) else False

                    print(f"{sql_statement[0][0].strip()} ...")
                    start = time.time()
                    
                    if is_update:
                        cur.execute("BEGIN BULK WRITE;")
                    cur.execute(query)
                    cur.execute("COMMIT;")
                    
                    end = time.time()
                    duration = end - start
                    print(f"-> {duration:.4f} seconds")
            except Exception:
                print(f"Error trying to execute query: {query}")

    def load_script(self, filename):
        with open(filename, "r") as f:
            return f.read()
    
    def load_mht(self, cur, csvpath):
        cur.execute("BEGIN BULK WRITE;")
        cur.execute(f"INSERT INTO Message_hasTag_Tag SELECT a, b, c FROM UMBRA.CSVVIEW('{csvpath}', 'DELIMITER \"|\", HEADER, NULL \"\", FORMAT text', 'a timestamp with time zone NOT NULL, b bigint NOT NULL, c bigint NOT NULL')")
        cur.execute("COMMIT;")

    def load_plm(self, cur, csvpath):
        cur.execute("BEGIN BULK WRITE;")
        cur.execute(f"INSERT INTO Person_likes_Message SELECT a, b, c FROM UMBRA.CSVVIEW('{csvpath}', 'DELIMITER \"|\", HEADER, NULL \"\", FORMAT text', 'a timestamp with time zone NOT NULL, b bigint NOT NULL, c bigint NOT NULL')")
        cur.execute("COMMIT;")

    def load_post(self, cur, csvpath):
        cur.execute("BEGIN BULK WRITE;")
        cur.execute(f"""
    INSERT INTO Message
    SELECT
        creationDate,
        id AS MessageId,
        id AS RootPostId,
        language AS RootPostLanguage,
        content,
        imageFile,
        locationIP,
        browserUsed,
        length,
        CreatorPersonId,
        ContainerForumId,
        LocationCountryId,
        NULL::bigint AS ParentMessageId
    FROM UMBRA.CSVVIEW(
        '{csvpath}',
        'DELIMITER "|", HEADER, NULL "", FORMAT text',
        'creationDate timestamp with time zone NOT NULL, id bigint NOT NULL, imageFile text, locationIP text NOT NULL, browserUsed text NOT NULL, language text, content text, length int NOT NULL, CreatorPersonId bigint NOT NULL, ContainerForumId bigint NOT NULL, LocationCountryId bigint NOT NULL'
        )
    """)
        cur.execute("COMMIT;")

    def load_initial_snapshot(self, pg_con, cur, data_dir, is_container):
        sql_copy_configuration = "(DELIMITER '|', HEADER, NULL '', FORMAT csv)"

        static_path = "initial_snapshot/static"
        dynamic_path = "initial_snapshot/dynamic"
        static_path_local = os.path.join(data_dir, static_path)
        dynamic_path_local = os.path.join(data_dir, dynamic_path)

        if is_container:
            path_prefix = "/data/"
        else:
            path_prefix = f"{data_dir}/"

        static_entities = ["Organisation", "Place", "Tag", "TagClass"]
        csv_entities =  ["Post", "Comment_hasTag_Tag", "Post_hasTag_Tag", "Person_likes_Comment", "Person_likes_Post"]
        dynamic_entities = ["Comment", "Forum", "Forum_hasMember_Person", "Forum_hasTag_Tag", "Person", "Person_hasInterest_Tag", "Person_knows_Person", "Person_studyAt_University", "Person_workAt_Company"]
        print("## Static entities")
        for entity in static_entities:
            print(f"===== {entity} =====")
            entity_dir = os.path.join(static_path_local, entity)
            csv_files = glob.glob(f'{entity_dir}/**/*.csv', recursive=True)
            if(not csv_files):
                raise ValueError(f"No CSV-files found for entity {entity}")
            for csv_file in csv_files:
                print(f"- {csv_file.rsplit('/', 1)[-1]}")
                csv_file_path = os.path.join(path_prefix, static_path, entity, os.path.basename(csv_file))

                start = time.time()
                cur.execute("BEGIN BULK WRITE;")
                cur.execute(f"COPY {entity} FROM '{csv_file_path}' {sql_copy_configuration}")
                cur.execute("COMMIT;")
                end = time.time()
                duration = end - start
                print(f"-> {duration:.4f} seconds")
        print("Loaded static entities.")

        for entity in ["Comment_hasTag_Tag", "Post_hasTag_Tag"]:
            entity_dir = os.path.join(dynamic_path_local, entity)
            csv_files = glob.glob(f'{entity_dir}/**/*.csv', recursive=True)
            for csv_file in csv_files:
                csv_file_path = os.path.join(path_prefix, dynamic_path, entity, os.path.basename(csv_file))
                self.load_mht(cur, csv_file_path)

        for entity in ["Person_likes_Comment", "Person_likes_Post"]:
            entity_dir = os.path.join(dynamic_path_local, entity)
            csv_files = glob.glob(f'{entity_dir}/**/*.csv', recursive=True)
            for csv_file in csv_files:
                csv_file_path = os.path.join(path_prefix, dynamic_path, entity, os.path.basename(csv_file))
                self.load_plm(cur, csv_file_path)

        for entity in ["Post"]:
            entity_dir = os.path.join(dynamic_path_local, entity)
            csv_files = glob.glob(f'{entity_dir}/**/*.csv', recursive=True)
            for csv_file in csv_files:
                csv_file_path = os.path.join(path_prefix, dynamic_path, entity, os.path.basename(csv_file))
                self.load_post(cur, csv_file_path)

        print("## Dynamic entities")
        for entity in dynamic_entities:
            print(f"===== {entity} =====")
            entity_dir = os.path.join(dynamic_path_local, entity)
            csv_files = glob.glob(f'{entity_dir}/**/*.csv', recursive=True)
            if(not csv_files):
                raise ValueError(f"No CSV-files found for entity {entity}")
            for csv_file in csv_files:
                print(f"- {csv_file.rsplit('/', 1)[-1]}")
                csv_file_path = os.path.join(path_prefix, dynamic_path, entity, os.path.basename(csv_file))

                start = time.time()
                cur.execute("BEGIN BULK WRITE;")
                cur.execute(f"COPY {entity} FROM '{csv_file_path}' {sql_copy_configuration}")
                cur.execute("COMMIT;")
                if entity == "Person_knows_Person":
                    cur.execute("BEGIN BULK WRITE;")
                    cur.execute(f"COPY {entity} (creationDate, Person2id, Person1id) FROM '{csv_file_path}' {sql_copy_configuration}")
                    cur.execute("COMMIT;")
                end = time.time()
                duration = end - start
                print(f"-> {duration:.4f} seconds")

        print("Loaded dynamic entities.")

    def main(self, data_dir, is_container):
        with psycopg.connect(
            dbname=self.database,
            host=self.endpoint,
            user=self.user,
            password=self.password,
            port=self.port
        ) as pg_con:
            pg_con.autocommit = True
            cur = pg_con.cursor()

            self.run_script(pg_con, cur, "ddl/drop-tables.sql")
            self.run_script(pg_con, cur, "ddl/schema-composite-merged-fk.sql")
            print("Load initial snapshot")
            self.load_initial_snapshot(pg_con, cur, data_dir, is_container)
            print("Maintain materialized views . . . ")
            self.run_script(pg_con, cur, "dml/maintain-views.sql")
            print("Done.")

            print("Create static materialized views . . . ")
            self.run_script(pg_con, cur, "dml/create-static-materialized-views.sql")
            print("Done.")

            #print("Add foreign key constraints")
            #self.run_script(pg_con, cur, "ddl/schema-foreign-keys.sql")


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument(
        '--UMBRA_CSV_DIR',
        help="UMBRA_CSV_DIR: folder containing the initial snapshot data to load e.g. '/out-sf1/graphs/csv/bi/composite-merged-fk'",
        type=str,
        required=True
    )
    parser.add_argument(
        '--is_container',
        dest='is_container',
        help="is_container: whether the data is loaded from within a container",
        action='store_true',
        required=False
    )
    parser.add_argument(
        '--no_is_container',
        dest='is_container',
        help="is_container: whether the data is loaded from within a container",
        action='store_false',
        required=False
    )
    parser.set_defaults(is_container=True)
    args = parser.parse_args()

    PGLoader = UmbraDbLoader()

    start = time.time()
    PGLoader.main(args.UMBRA_CSV_DIR, args.is_container)
    end = time.time()
    duration = end - start
    print(f"Loaded data in Umbra in {duration:.4f} seconds")
