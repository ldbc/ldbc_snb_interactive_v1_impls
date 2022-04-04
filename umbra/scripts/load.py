import psycopg2
import sys
import re
import os
import time

if len(sys.argv) < 2:
    print("Umbra loader script")
    print("Usage: load.py <UMBRA_DATA_DIR> [--compressed]")
    exit(1)

data_dir = sys.argv[1]
local = len(sys.argv) == 3 and sys.argv[2] == "--local"
pg_host = os.getenv("UMBRA_HOST", default="localhost")
pg_con = psycopg2.connect(host=pg_host, user="postgres", password="mysecretpassword", port=5432)
con = pg_con.cursor()

def run_script(con, filename):
    with open(filename, "r") as f:
        queries_file = f.read()
        queries = queries_file.split(";")
        for query in queries:
            if query.isspace():
                continue

            sql_statement = re.findall(r"^((CREATE|INSERT|DROP|DELETE|SELECT|COPY) [A-Za-z0-9_ ]*)", query, re.MULTILINE)
            print(f"{sql_statement[0][0].strip()} ...")

            start = time.time()
            con.execute(query)
            end = time.time()
            duration = end - start
            print(f"-> {duration:.4f} seconds")

print("Load initial snapshot")
run_script(con, "ddl/load.sql")

print("Creating materialized views . . . ")
run_script(con, "ddl/schema_constraints.sql")
pg_con.commit()
print("Done.")

print("Loaded initial snapshot to Umbra.")
