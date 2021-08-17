#!/usr/bin/env python3

import argparse
import os
import psycopg2


def vacuum(con, pg_con):
    old_isolation_level = con.isolation_level
    pg_con.set_isolation_level(psycopg2.extensions.ISOLATION_LEVEL_AUTOCOMMIT)
    pg_con.cursor().execute("VACUUM FULL")
    pg_con.set_isolation_level(old_isolation_level)


def load_script(filename):
    with open(filename, "r") as f:
        return f.read()


def main(argv=None):
    parser = argparse.ArgumentParser()
    parser.add_argument(
        "--db",
        default=os.environ.get("POSTGRES_DATABASE", "ldbcsnb"),
        help="PostgreSQL database",
    )
    parser.add_argument(
        "--host", default="localhost", help="PostgreSQL host",
    )
    parser.add_argument(
        "--port",
        type=int,
        default=int(os.environ.get("POSTGRES_PORT", 5432)),
        help="PostgreSQL port",
    )
    parser.add_argument(
        "--user",
        default=os.environ.get("POSTGRES_USER", "postgres"),
        help="PostgreSQL user name",
    )
    parser.add_argument(
        "--password",
        default=os.environ.get("POSTGRES_PASSWORD", "mysecretpassword"),
        help="PostgreSQL user password",
    )
    options = parser.parse_args(argv)

    print("Running Postgres / psycopg2")

    print("Datagen / load initial data set using SQL")

    pg_con = psycopg2.connect(
        database=options.db,
        host=options.host,
        user=options.user,
        password=options.password,
        port=options.port,
    )
    try:
        with pg_con.cursor() as con:
            con.execute(load_script("ddl/schema.sql"))
            con.execute(load_script("ddl/snb-load.sql"))
            pg_con.commit()

            con.execute(load_script("ddl/schema_constraints.sql"))
            con.execute(load_script("ddl/schema_foreign_keys.sql"))
            pg_con.commit()

            print("Vacuuming")
            vacuum(pg_con)
    finally:
        pg_con.close()

    print("Loaded initial snapshot")


if __name__ == "__main__":
    main()
