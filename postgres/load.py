import psycopg2
import sys
import os

def vacuum(con):
    old_isolation_level = con.isolation_level
    pg_con.set_isolation_level(psycopg2.extensions.ISOLATION_LEVEL_AUTOCOMMIT)
    pg_con.cursor().execute("VACUUM FULL")
    pg_con.set_isolation_level(old_isolation_level)

print("Running Postgres / psycopg2")

print("Datagen / load initial data set using SQL")

pg_con = psycopg2.connect(database="ldbcsnb", host="localhost", user="postgres", password="mysecretpassword",  port=5432)
con = pg_con.cursor()

def load_script(filename):
    with open(filename, "r") as f:
        return f.read()

con.execute(load_script("ddl/schema.sql"))
con.execute(load_script("ddl/snb-load.sql"))
pg_con.commit()

con.execute(load_script("ddl/schema_constraints.sql"))
con.execute(load_script("ddl/schema_foreign_keys.sql"))
pg_con.commit()

print("Vacuuming")
vacuum(pg_con)

print("Loaded initial snapshot")
