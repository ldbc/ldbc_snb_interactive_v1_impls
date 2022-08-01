#!/usr/bin/env python3

import os
import psycopg
import re
import time

class PostgresDbLoader():

    def __init__(self):
        self.database = os.environ.get("POSTGRES_DB", "ldbcsnb")
        self.endpoint = os.environ.get("POSTGRES_HOST", "localhost")
        self.port = int(os.environ.get("POSTGRES_PORT", 5432))
        self.user = os.environ.get("POSTGRES_USER", "postgres")
        self.password = os.environ.get("POSTGRES_PASSWORD", "mysecretpassword")

    def run_script(self, conn, filename):

        with open(filename, "r") as f:
            try:
                queries_file = f.read()
                # strip comments
                queries_file = re.sub(r"(\n|^)--.*", "", queries_file)
                queries = queries_file.split(";")
                for query in queries:
                    if query.isspace():
                        continue

                    sql_statement = re.findall(r"^((CREATE|INSERT|DROP|DELETE|SELECT|COPY) [A-Za-z0-9_ ]*)", query, re.MULTILINE|re.IGNORECASE)
                    print(f"{sql_statement[0][0].strip()} ...")
                    start = time.time()
                    cur = conn.cursor()
                    cur.execute(query)
                    conn.commit()
                    end = time.time()
                    duration = end - start
                    print(f"-> {duration:.4f} seconds")
            except Exception:
                print(f"Error trying to execute query: {query};")
                raise


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
        ) as conn:
            print("Loading initial data set")
            self.run_script(conn, "ddl/schema.sql")
            self.run_script(conn, "ddl/load.sql")

            print("Adding indexes and constraints")
            self.run_script(conn, "ddl/schema_constraints.sql")
            self.run_script(conn, "ddl/schema_foreign_keys.sql")

            print("Vacuuming")
            self.vacuum(conn)
            conn.commit()
        print("Loaded initial snapshot")

    def load_script(self, filename):
        with open(filename, "r") as f:
            return f.read()


if __name__ == "__main__":
    PGLoader = PostgresDbLoader()
    PGLoader.main()
