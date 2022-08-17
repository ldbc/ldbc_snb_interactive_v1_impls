import os
import duckdb

parquet_path = "factors/"
con = duckdb.connect(database="scratch/factors.duckdb")

print("============ Loading the factor tables ============")
for entity in [
    # "cityNumPersons",
    # "cityPairsNumFriends",
    # "companyNumEmployees",
    # "countryNumMessages",
    "countryNumPersons",
    "countryPairsNumFriends",                    # Q3a, Q3b
    # "creationDayAndLengthCategoryNumMessages",
    # "creationDayAndTagClassNumMessages",
    # "creationDayAndTagNumMessages",
    "creationDayNumMessages",
    # "languageNumPosts",
    # "lengthNumMessages",
    # "people2Hops",
    "people4Hops",                               # Q13b, Q14b
    # "personDisjointEmployerPairs",
    "personFirstNames",                          # Q1
    "personKnowsPersonConnected",                # Q13a, Q14a
    "personLikesNumMessages",
    "personNumFriendComments",
    "personNumFriendOfFriendCompanies",
    "personNumFriendOfFriendForums",
    "personNumFriendOfFriendPosts",
    "personNumFriendTags",
    "personNumFriends",
    "personNumFriendsOfFriends",
    # "sameUniversityConnected",
    # "tagClassNumMessages",
    "tagClassNumTags",
    # "tagNumMessages",
    "tagNumPersons",
    ]:
    print(f"{entity}")
    for parquet_file in [f for f in os.listdir(f"{parquet_path}{entity}/") if f.endswith(".parquet")]:
        print(f"- {parquet_file}")
        con.execute(f"DROP TABLE IF EXISTS {entity}")
        con.execute(f"CREATE TABLE {entity} AS SELECT * FROM read_parquet('{parquet_path}{entity}/{parquet_file}')")

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
for query_variant in ["1", "2", "3a", "3b", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13a", "13b", "14a", "14b"]:
    print(f"- Q{query_variant}")
    with open(f"paramgen-queries/pg-{query_variant}.sql", "r") as parameter_query_file:
        parameter_query = parameter_query_file.read()
        con.execute(f"COPY ( {parameter_query} ) TO '../parameters/interactive-{query_variant}.parquet' WITH (FORMAT PARQUET);")
