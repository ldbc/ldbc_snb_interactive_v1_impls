#!/usr/bin/env python3
import glob
from multiprocessing.sharedctypes import Value
import os
import re
import time
import argparse
import psycopg

class PostgresDbLoader():

    def __init__(self):
        self.database = os.environ.get("POSTGRES_DB", "ldbcsnb")
        self.endpoint = os.environ.get("POSTGRES_HOST", "localhost")
        self.port = int(os.environ.get("POSTGRES_PORT", 5432))
        self.user = os.environ.get("POSTGRES_USER", "postgres")
        self.password = os.environ.get("POSTGRES_PASSWORD", "mysecretpassword")

    def vacuum(self, conn):
        conn.autocommit=True
        conn.cursor().execute("ANALYZE")
        conn.autocommit=False

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

                    sql_statement = re.findall(r"^((CREATE|INSERT|DROP|DELETE|SELECT|COPY) [A-Za-z0-9_ ]*)", query, re.MULTILINE)
                    print(f"{sql_statement[0][0].strip()} ...")
                    start = time.time()
                    cur.execute(query)
                    pg_con.commit()
                    end = time.time()
                    duration = end - start
                    print(f"-> {duration:.4f} seconds")
            except Exception:
                print(f"Error trying to execute query: {query}")

    def load_script(self, filename):
        with open(filename, "r") as f:
            return f.read()

    def load_initial_snapshot(self, pg_con, cur, data_dir):

        static_path = "initial_snapshot/static"
        dynamic_path = "initial_snapshot/dynamic"
        static_path_local = os.path.join(data_dir, static_path)
        dynamic_path_local = os.path.join(data_dir, dynamic_path)

        static_path_container = "/data/" + static_path
        dynamic_path_container = "/data/" + dynamic_path

        static_entities = ["Organisation", "Place", "Tag", "TagClass"]
        dynamic_entities = ["Comment", "Comment_hasTag_Tag", "Forum", "Forum_hasMember_Person", "Forum_hasTag_Tag", "Person", "Person_hasInterest_Tag", "Person_knows_Person", "Person_likes_Comment", "Person_likes_Post", "Person_studyAt_University", "Person_workAt_Company", "Post", "Post_hasTag_Tag"]
        print("## Static entities")
        for entity in static_entities:
            print(f"===== {entity} =====")
            entity_dir = os.path.join(static_path_local, entity)
            print(f"--> {entity_dir}")
            csv_files = glob.glob(f'{entity_dir}/**/*.csv', recursive=True)
            if(not csv_files):
                raise ValueError(f"No CSV-files found for entity {entity}")
            for csv_file in csv_files:
                print(f"- {csv_file}")
                cur.execute(f"COPY {entity} FROM '{os.path.join(static_path_container, entity, os.path.basename(csv_file))}' (DELIMITER '|', HEADER, NULL '', FORMAT csv)")
                pg_con.commit()
        print("Loaded static entities.")

        print("## Dynamic entities")
        for entity in dynamic_entities:
            print(f"===== {entity} =====")
            entity_dir = os.path.join(dynamic_path_local, entity)
            print(f"--> {entity_dir}")
            csv_files = glob.glob(f'{entity_dir}/**/*.csv', recursive=True)
            if(not csv_files):
                raise ValueError(f"No CSV-files found for entity {entity}")
            for csv_file in csv_files:
                print(f"- {csv_file}")
                container_path = os.path.join(dynamic_path_container, entity, os.path.basename(csv_file))
                cur.execute(f"COPY {entity} FROM '{container_path}' (DELIMITER '|', HEADER, NULL '', FORMAT csv)")
                if entity == "Person_knows_Person":
                    cur.execute(f"COPY {entity} (creationDate, Person2id, Person1id) FROM '{container_path}' (DELIMITER '|', HEADER, NULL '', FORMAT csv)")
                pg_con.commit()
        print("Loaded dynamic entities.")

    def main(self, data_dir):
        with psycopg.connect(
            dbname=self.database,
            host=self.endpoint,
            user=self.user,
            password=self.password,
            port=self.port
        ) as pg_con:
            cur = pg_con.cursor()

            self.run_script(pg_con, cur, "ddl/drop-tables.sql")
            self.run_script(pg_con, cur, "ddl/schema-composite-merged-fk.sql")
            self.run_script(pg_con, cur, "ddl/schema-delete-candidates.sql")
            print("Load initial snapshot")
            self.load_initial_snapshot(pg_con, cur, data_dir)
            print("Maintain materialized views . . . ")
            self.run_script(pg_con, cur, "dml/maintain-views.sql")
            print("Done.")

            print("Create static materialized views . . . ")
            self.run_script(pg_con, cur, "dml/create-static-materialized-views.sql")
            print("Done.")

            print("Adding indexes and constraints")
            cur.execute(self.load_script("ddl/schema-constraints.sql"))
            cur.execute(self.load_script("ddl/schema-foreign-keys.sql"))
            pg_con.commit()

            print("Loaded initial snapshot to Postgres.")


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument(
        '--POSTGRES_DATA_DIR',
        help="POSTGRES_DATA_DIR: folder containing the initial snapshot data to load e.g. '/out-sf1/graphs/csv/bi/composite-merged-fk'",
        type=str,
        required=True
    )
    parser.add_argument(
        '--is_container',
        help="is_container: whether the data is loaded from within a container",
        type=str,
        required=False,
        default=False
    )
    args = parser.parse_args()

    PGLoader = PostgresDbLoader()

    if (args.is_container):
        data_dir = "/data/"
    else:
        data_dir = args.POSTGRES_DATA_DIR

    start = time.time()
    PGLoader.main(data_dir)
    end = time.time()
    duration = end - start
    print(f"Data loaded in {duration:.4f} seconds")