#!/usr/bin/env python3
import sys
import os
import re
import time
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

    def main(self):
        with psycopg.connect(
            dbname=self.database,
            host=self.endpoint,
            user=self.user,
            password=self.password,
            port=self.port
        ) as pg_con:
            if len(sys.argv) < 2:
                print("Postgres loader script")
                print("Usage: load.py <POSTGRES_DATA_DIR> [--compressed]")
                exit(1)

            data_dir = sys.argv[1]
            local = len(sys.argv) == 3 and sys.argv[2] == "--local"

            cur = pg_con.cursor()

            def run_script(pg_con, cur, filename):
                with open(filename, "r") as f:
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

            run_script(pg_con, cur, "ddl/drop-tables.sql")
            run_script(pg_con, cur, "ddl/schema-composite-merged-fk.sql")
            run_script(pg_con, cur, "ddl/schema-delete-candidates.sql")

            print("Load initial snapshot")

            # initial snapshot
            static_path = f"{data_dir}/initial_snapshot/static"
            dynamic_path = f"{data_dir}/initial_snapshot/dynamic"
            static_entities = ["Organisation", "Place", "Tag", "TagClass"]
            dynamic_entities = ["Comment", "Comment_hasTag_Tag", "Forum", "Forum_hasMember_Person", "Forum_hasTag_Tag", "Person", "Person_hasInterest_Tag", "Person_knows_Person", "Person_likes_Comment", "Person_likes_Post", "Person_studyAt_University", "Person_workAt_Company", "Post", "Post_hasTag_Tag"]

            if local:
                dbs_data_dir = data_dir
            else:
                dbs_data_dir = '/data'

            print("## Static entities")
            for entity in static_entities:
                for csv_file in [f for f in os.listdir(f"{static_path}/{entity}") if f.startswith("part-") and f.endswith(".csv")]:
                    csv_path = f"{entity}/{csv_file}"
                    print(f"- {csv_path}")
                    #print(f"- {csv_path}", end='\r')
                    cur.execute(f"COPY {entity} FROM '{dbs_data_dir}/initial_snapshot/static/{entity}/{csv_file}' (DELIMITER '|', HEADER, NULL '', FORMAT csv)")
                    #print(" " * 120, end='\r')
                    pg_con.commit()
            print("Loaded static entities.")

            print("## Dynamic entities")
            for entity in dynamic_entities:
                for csv_file in [f for f in os.listdir(f"{dynamic_path}/{entity}") if f.startswith("part-") and f.endswith(".csv")]:
                    csv_path = f"{entity}/{csv_file}"
                    print(f"- {csv_path}")
                    #print(f"- {csv_path}", end='\r')
                    cur.execute(f"COPY {entity} FROM '{dbs_data_dir}/initial_snapshot/dynamic/{entity}/{csv_file}' (DELIMITER '|', HEADER, NULL '', FORMAT csv)")
                    if entity == "Person_knows_Person":
                        cur.execute(f"COPY {entity} (creationDate, Person2id, Person1id) FROM '{dbs_data_dir}/initial_snapshot/dynamic/{entity}/{csv_file}' (DELIMITER '|', HEADER, NULL '', FORMAT csv)")
                    #print(" " * 120, end='\r')
                    pg_con.commit()
            print("Loaded dynamic entities.")

            print("Maintain materialized views . . . ")
            run_script(pg_con, cur, "dml/maintain-views.sql")
            print("Done.")

            print("Create static materialized views . . . ")
            run_script(pg_con, cur, "dml/create-static-materialized-views.sql")
            print("Done.")

            print("Loaded initial snapshot to Postgres.")


    def load_script(self, filename):
        with open(filename, "r") as f:
            return f.read()


if __name__ == "__main__":
    PGLoader = PostgresDbLoader()
    PGLoader.main()

