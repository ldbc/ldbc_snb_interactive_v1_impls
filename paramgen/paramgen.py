import os
import duckdb

csv_path = "factors/"
con = duckdb.connect(database='factors.duckdb')

temporal_parquet_path = "temporal/"

print("============ Initializing database ============")
with open(f"paramgen-queries/ddl/schema.sql", "r") as schema_file:
    con.execute(schema_file.read())

print()
print("============ Loading the factor tables ============")
for entity in ["cityNumPersons", "cityPairsNumFriends", "companyNumEmployees", "countryNumMessages", "countryNumPersons", "countryPairsNumFriends", "creationDayAndLengthCategoryNumMessages", "creationDayAndTagNumMessages", "creationDayAndTagClassNumMessages", "creationDayNumMessages", "languageNumPosts", "lengthNumMessages", "people2Hops", "people4Hops", "personDisjointEmployerPairs", "personNumFriends", "tagClassNumMessages", "tagClassNumTags", "tagNumMessages", "tagNumPersons"]:
    print(f"{entity}")
    for csv_file in [f for f in os.listdir(f"{csv_path}{entity}/") if f.endswith(".csv")]:
        print(f"- {csv_file}")
        con.execute(f"COPY {entity} FROM '{csv_path}{entity}/{csv_file}' (FORMAT CSV, DELIMITER ',')")

print()
print("============ Loading the temporal tables ============")
print("Person_studyAt_University")
for parquet_file in [f for f in os.listdir(f"{temporal_parquet_path}Person_studyAt_University/") if f.endswith(".snappy.parquet")]:
    print(f"- {parquet_file}")
    con.execute(f"""
        INSERT INTO Person_studyAt_University_window (
            SELECT PersonId, UniversityId
            FROM read_parquet('{temporal_parquet_path}Person_studyAt_University/{parquet_file}')
            WHERE to_timestamp(creationDate/1000) < TIMESTAMP '2012-11-29'
              AND to_timestamp(deletionDate/1000) > TIMESTAMP '2013-01-01'
        );
        """)

print("Person_workAt_Company")
for parquet_file in [f for f in os.listdir(f"{temporal_parquet_path}Person_workAt_Company/") if f.endswith(".snappy.parquet")]:
    print(f"- {parquet_file}")
    con.execute(f"""
        INSERT INTO Person_workAt_Company_window (
            SELECT personId, companyId
            FROM read_parquet('{temporal_parquet_path}Person_workAt_Company/{parquet_file}')
            WHERE to_timestamp(creationDate/1000) < TIMESTAMP '2012-11-29'
              AND to_timestamp(deletionDate/1000) > TIMESTAMP '2013-01-01'
        );
        """)

print("Person")
for parquet_file in [f for f in os.listdir(f"{temporal_parquet_path}Person/") if f.endswith(".snappy.parquet")]:
    print(f"- {parquet_file}")
    con.execute(f"""
        INSERT INTO person_window (
            SELECT id
            FROM read_parquet('{temporal_parquet_path}Person/{parquet_file}')
            WHERE to_timestamp(creationDate/1000) < TIMESTAMP '2012-11-29'
              AND to_timestamp(deletionDate/1000) > TIMESTAMP '2013-01-01'
        );
        """)

print("Person_knows_Person")
for parquet_file in [f for f in os.listdir(f"{temporal_parquet_path}Person_knows_Person/") if f.endswith(".snappy.parquet")]:
    print(f"- {parquet_file}")
    con.execute(f"""
        INSERT INTO knows_window (
            SELECT person1Id, person2Id
            FROM read_parquet('{temporal_parquet_path}Person_knows_Person/{parquet_file}')
            WHERE to_timestamp(creationDate/1000) < TIMESTAMP '2012-11-29'
              AND to_timestamp(deletionDate/1000) > TIMESTAMP '2013-01-01'
        );
        """)

print()
print("============ Creating materialized views ============")
# d: drop
# m: materialize
for query_variant in ["2d", "2m", "8d", "8m", "20d", "20m"]:
    print(f"- Q{query_variant}")
    with open(f"paramgen-queries/pg-{query_variant}.sql", "r") as parameter_query_file:
        parameter_query = parameter_query_file.read()
        con.execute(parameter_query)

print()
print("============ Generating parameters ============")
for query_variant in ["1", "2a", "2b", "3", "4", "5", "6", "7", "8a", "8b", "9", "10a", "10b", "11", "12", "13", "14a", "14b", "15a", "15b", "16a", "16b", "17", "18", "19a", "19b", "20"]:
    print(f"- Q{query_variant}")
    with open(f"paramgen-queries/pg-{query_variant}.sql", "r") as parameter_query_file:
        parameter_query = parameter_query_file.read()
        con.execute(f"COPY ( {parameter_query} ) TO '../parameters/bi-{query_variant}.csv' WITH (HEADER, DELIMITER '|');")
