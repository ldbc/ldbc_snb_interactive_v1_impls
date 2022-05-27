import psycopg
import re
import os
import time

class UmbraDbLoader():

    def __init__(self):
        self.endpoint = os.environ.get("UMBRA_HOST", "localhost")
        self.port = int(os.environ.get("UMBRA_PORT", 5432))
        self.user = os.environ.get("UMBRA_USER", "postgres")
        self.password = os.environ.get("UMBRA_PASSWORD", "mysecretpassword")

    def load_data(self):
        with psycopg.connect(
            host=self.endpoint,
            user=self.user,
            password=self.password,
            port=self.port
        ) as pg_con:

            print("Load initial snapshot")
            loader.run_script(pg_con, "ddl/load.sql")

            print("Creating materialized views . . . ")
            loader.run_script(pg_con, "ddl/schema_constraints.sql")
            pg_con.commit()
            print("Done.")

            print("Loaded initial snapshot to Umbra.")


    def run_script(self, pg_con, filename):
        cur = pg_con.cursor()
        with open(filename, "r") as f:
            queries_file = f.read()
            queries = queries_file.split(";")
            for query in queries:
                if query.isspace():
                    continue

                sql_statement = re.findall(r"^((CREATE|INSERT|DROP|DELETE|SELECT|COPY) [A-Za-z0-9_ ]*)", query, re.MULTILINE)
                print(f"{sql_statement[0][0].strip()} ...")

                start = time.time()
                cur.execute(query)
                end = time.time()
                duration = end - start
                print(f"-> {duration:.4f} seconds")


if __name__ == "__main__":
    loader = UmbraDbLoader()
    loader.load_data()
