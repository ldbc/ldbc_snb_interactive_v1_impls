#!/usr/bin/env python3

import argparse
import os
import psycopg2


def vacuum(pg_con):
    old_isolation_level = pg_con.isolation_level
    pg_con.set_isolation_level(psycopg2.extensions.ISOLATION_LEVEL_AUTOCOMMIT)
    pg_con.cursor().execute("ANALYZE")
    pg_con.set_isolation_level(old_isolation_level)


def load_script(filename):
    with open(filename, "r") as f:
        return f.read()

def execute_load_script(filename, con):
    csv_dir = os.environ.get("HYPER_CSV_DIR", "")
    print(csv_dir)
    if not csv_dir:
        raise ValueError("No HYPER_CSV_DIR defined.")

    with open(filename, "r") as f:
        contents = f.read()
        contents = contents.replace("/data", csv_dir)

    queries = contents.split(";")
    for query in queries:
        if query.isspace():
            continue
        print(query)
        con.execute(query)

def main(argv=None):
    parser = argparse.ArgumentParser()
    parser.add_argument(
        "--db",
        default=os.environ.get("HYPER_DATABASE", "ldbcsnb"),
        help="HyPer database",
    )
    parser.add_argument(
        "--host",
        default=os.environ.get("HYPER_HOST", "localhost"),
        help="HyPer host",
    )
    parser.add_argument(
        "--port",
        type=int,
        default=int(os.environ.get("HYPER_PORT", 7484)),
        help="HyPer port",
    )
    parser.add_argument(
        "--user",
        default=os.environ.get("HYPER_USER", "ldbcuser"),
        help="HyPer user name",
    )
    parser.add_argument(
        "--password",
        default=os.environ.get("HYPER_PASSWORD", None),
        help="HyPer user password",
    )
    options = parser.parse_args(argv)

    print("Running Postgres / psycopg2")
    print(options)
    pg_con = psycopg2.connect(
        host=options.host,
        user=options.user,
        port=options.port,
    )
    try:
        with pg_con.cursor() as con:
            print("Loading initial data set")
            con.execute(load_script("ddl/schema.sql"))
            execute_load_script("ddl/load.sql", con)
            pg_con.commit()
            execute_load_script("ddl/load_2.sql", con)
            pg_con.commit()
            execute_load_script("ddl/load_3.sql", con)
            pg_con.commit()
            execute_load_script("ddl/load_4.sql", con)
            pg_con.commit()
            execute_load_script("ddl/load_5.sql", con)
            pg_con.commit()
            print("Adding indexes and constraints")

            print("Vacuuming")
            vacuum(pg_con)
    finally:
        pg_con.close()

    print("Loaded initial snapshot")


if __name__ == "__main__":
    main()
