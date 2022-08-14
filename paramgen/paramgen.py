import os
import duckdb

parquet_path = "factors/"
con = duckdb.connect(database='factors.duckdb')

temporal_parquet_path = "temporal/"

print("============ Initializing database ============")
with open(f"paramgen-queries/ddl/schema.sql", "r") as schema_file:
    con.execute(schema_file.read())

print()
print("============ Loading the factor tables ============")
for entity in ["cityNumPersons", "cityPairsNumFriends", "companyNumEmployees", "countryNumMessages", "countryNumPersons", "countryPairsNumFriends", "creationDayAndLengthCategoryNumMessages", "creationDayAndTagNumMessages", "creationDayAndTagClassNumMessages", "creationDayNumMessages", "languageNumPosts", "lengthNumMessages", "people2Hops", "people4Hops", "personDisjointEmployerPairs", "personFirstNames", "personNumFriends", "tagClassNumMessages", "tagClassNumTags", "tagNumMessages", "tagNumPersons"]:
    print(f"{entity}")
    for parquet_file in [f for f in os.listdir(f"{parquet_path}{entity}/") if f.endswith(".parquet")]:
        print(f"- {parquet_file}")
        con.execute(f"COPY {entity} FROM '{parquet_path}{entity}/{parquet_file}'")

print()
print("============ Creating materialized views ============")
# d: drop
# m: materialize
for query_variant in []:
    print(f"- Q{query_variant}")
    with open(f"paramgen-queries/pg-{query_variant}.sql", "r") as parameter_query_file:
        parameter_query = parameter_query_file.read()
        con.execute(parameter_query)

print()
print("============ Generating parameters ============")
for query_variant in ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14"]:
    print(f"- Q{query_variant}")
    with open(f"paramgen-queries/pg-{query_variant}.sql", "r") as parameter_query_file:
        parameter_query = parameter_query_file.read()
        con.execute(f"COPY ( {parameter_query} ) TO '../parameters/interactive-{query_variant}.parquet' WITH (FORMAT PARQUET);")
