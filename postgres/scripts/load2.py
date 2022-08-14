#!/usr/bin/env python3
"""
FILE: load2.py
DESC: Alternative loading script using psycopg copy construct to load the CSV
      file with the advantage that the paths are from the client, not requiring
      the data to be present at the Postgres instance
"""
import glob
import os
import re
import time
import argparse
import psycopg

class PostgresDbLoader():
    # Linux uses 4096 as default block size
    # Postgres uses 8192
    BLOCK_SIZE = 4096

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

    def copy_csv(self, cur, copy_command, csv_file):
        """
        Copy a local CSV file to Postgres
        Args:
            - cur (Cursor): the cursor object
            - copy_command (str): The COPY statement to execute
            - csv_file (str): The CSV-file to copy
        """
        with open(csv_file, "r") as csv:
            with cur.copy(copy_command) as copy:
                while data := csv.read(self.BLOCK_SIZE):
                    copy.write(data)

    def load_initial_snapshot(self, pg_con, cur, data_dir):
        static_path_local = os.path.join(data_dir, "initial_snapshot/static")
        dynamic_path_local = os.path.join(data_dir, "initial_snapshot/dynamic")
        static_entities = ["Organisation", "Place", "Tag", "TagClass"]
        dynamic_entities = ["Comment", "Comment_hasTag_Tag", "Forum", "Forum_hasMember_Person", "Forum_hasTag_Tag", "Person", "Person_hasInterest_Tag", "Person_knows_Person", "Person_likes_Comment", "Person_likes_Post", "Person_studyAt_University", "Person_workAt_Company", "Post", "Post_hasTag_Tag"]
        print("## Static entities")
        for entity in static_entities:
            print(f"===== {entity} =====")
            entity_dir = os.path.join(static_path_local, entity)
            print(f"--> {entity_dir}")
            for csv_file in glob.glob(f'{entity_dir}/*.csv', recursive=True):
                print(f"- {csv_file}")
                self.copy_csv(cur, f"COPY {entity} FROM STDIN (DELIMITER '|', HEADER, NULL '', FORMAT csv)", csv_file)
                pg_con.commit()
        print("Loaded static entities.")

        print("## Dynamic entities")
        for entity in dynamic_entities:
            print(f"===== {entity} =====")
            entity_dir = os.path.join(dynamic_path_local, entity)
            print(f"--> {entity_dir}")
            for csv_file in glob.glob(f'{entity_dir}/*.csv', recursive=True):
                print(f"- {csv_file}")
                self.copy_csv(cur, f"COPY {entity} FROM STDIN (DELIMITER '|', HEADER, NULL '', FORMAT csv)", csv_file)
                if entity == "Person_knows_Person":
                    self.copy_csv(cur, f"COPY {entity} (creationDate, Person2id, Person1id) FROM STDIN (DELIMITER '|', HEADER, NULL '', FORMAT csv)", csv_file)
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
        '--POSTGRES_CSV_DIR',
        help="POSTGRES_CSV_DIR: folder containing the initial snapshot data to load e.g. '/out-sf1/graphs/csv/bi/composite-merged-fk'",
        type=str,
        required=True
    )
    args = parser.parse_args()

    PGLoader = PostgresDbLoader()
    start = time.time()
    PGLoader.main(args.POSTGRES_CSV_DIR)
    end = time.time()
    duration = end - start
    print(f"Data loaded in {duration:.4f} seconds")