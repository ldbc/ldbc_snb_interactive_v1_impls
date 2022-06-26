#!/usr/bin/env python3

import os
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
        ) as conn:
            with conn.cursor() as cur:
                print("Loading initial data set")
                cur.execute(self.load_script("ddl/schema.sql"))
                cur.execute(self.load_script("ddl/load.sql"))
                conn.commit()

                print("Adding indexes and constraints")
                cur.execute(self.load_script("ddl/schema_constraints.sql"))
                cur.execute(self.load_script("ddl/schema_foreign_keys.sql"))
                conn.commit()

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
