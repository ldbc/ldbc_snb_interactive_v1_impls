import duckdb
from datetime import timedelta, datetime
import glob
from pathlib import Path

remove_duplicates_dict = {
    "Q_1"   : "DELETE FROM Q_1   t1 WHERE t1.useUntil < (SELECT max(t2.useUntil) FROM Q_1   t2 WHERE t2.personId  = t1.personId  AND t2.firstName = t1.firstName);",
    "Q_2"   : "DELETE FROM Q_2   t1 WHERE t1.useUntil < (SELECT max(t2.useUntil) FROM Q_2   t2 WHERE t2.personId  = t1.personId  AND t2.maxDate = t1.maxDate);",
    "Q_3a"  : "DELETE FROM Q_3a  t1 WHERE t1.useUntil < (SELECT max(t2.useUntil) FROM Q_3a  t2 WHERE t2.personId  = t1.personId  AND t2.countryXName = t1.countryXName AND t2.countryYName = t1.countryYName AND t2.startDate = t1.startDate AND t2.durationDays = t1.durationDays);",
    "Q_3b"  : "DELETE FROM Q_3b  t1 WHERE t1.useUntil < (SELECT max(t2.useUntil) FROM Q_3b  t2 WHERE t2.personId  = t1.personId  AND t2.countryXName = t1.countryXName AND t2.countryYName = t1.countryYName AND t2.startDate = t1.startDate AND t2.durationDays = t1.durationDays);",
    "Q_4"   : "DELETE FROM Q_4   t1 WHERE t1.useUntil < (SELECT max(t2.useUntil) FROM Q_4   t2 WHERE t2.personId  = t1.personId  AND t2.startDate = t1.startDate AND t2.durationDays = t1.durationDays);",
    "Q_5"   : "DELETE FROM Q_5   t1 WHERE t1.useUntil < (SELECT max(t2.useUntil) FROM Q_5   t2 WHERE t2.personId  = t1.personId  AND t2.minDate = t1.minDate);",
    "Q_6"   : "DELETE FROM Q_6   t1 WHERE t1.useUntil < (SELECT max(t2.useUntil) FROM Q_6   t2 WHERE t2.personId  = t1.personId  AND t2.tagName = t1.tagName);",
    "Q_7"   : "DELETE FROM Q_7   t1 WHERE t1.useUntil < (SELECT max(t2.useUntil) FROM Q_7   t2 WHERE t2.personId  = t1.personId);",
    "Q_8"   : "DELETE FROM Q_8   t1 WHERE t1.useUntil < (SELECT max(t2.useUntil) FROM Q_8   t2 WHERE t2.personId  = t1.personId);",
    "Q_9"   : "DELETE FROM Q_9   t1 WHERE t1.useUntil < (SELECT max(t2.useUntil) FROM Q_9   t2 WHERE t2.personId  = t1.personId  AND t2.maxDate = t1.maxDate);",
    "Q_10"  : "DELETE FROM Q_10  t1 WHERE t1.useUntil < (SELECT max(t2.useUntil) FROM Q_10  t2 WHERE t2.personId  = t1.personId  AND t2.month = t1.month);",
    "Q_11"  : "DELETE FROM Q_11  t1 WHERE t1.useUntil < (SELECT max(t2.useUntil) FROM Q_11  t2 WHERE t2.personId  = t1.personId  AND t2.countryName = t1.countryName AND t2.workFromYear = t1.workFromYear);",
    "Q_12"  : "DELETE FROM Q_12  t1 WHERE t1.useUntil < (SELECT max(t2.useUntil) FROM Q_12  t2 WHERE t2.personId  = t1.personId  AND t2.tagClassName = t1.tagClassName);",
    "Q_13a" : "DELETE FROM Q_13a t1 WHERE t1.useUntil < (SELECT max(t2.useUntil) FROM Q_13a t2 WHERE t2.person1Id = t1.person1Id AND t2.person2Id = t1.person2Id);",
    "Q_13b" : "DELETE FROM Q_13b t1 WHERE t1.useUntil < (SELECT max(t2.useUntil) FROM Q_13b t2 WHERE t2.person1Id = t1.person1Id AND t2.person2Id = t1.person2Id);",
    "Q_14a" : "DELETE FROM Q_14a t1 WHERE t1.useUntil < (SELECT max(t2.useUntil) FROM Q_14a t2 WHERE t2.person1Id = t1.person1Id AND t2.person2Id = t1.person2Id);",
    "Q_14b" : "DELETE FROM Q_14b t1 WHERE t1.useUntil < (SELECT max(t2.useUntil) FROM Q_14b t2 WHERE t2.person1Id = t1.person1Id AND t2.person2Id = t1.person2Id);"
}

def generate_parameters(cursor, date_limit, date_start, create_tables, query_variant):
    """
    Creates parameters for given query variant.
    Args:
        - cursor (DuckDBPyConnection): cursor to the DuckDB instance
        - date_limit (datetime): The day to filter on. This date will be used to compare creation and deletion dates
        - date_start (datetime): The first day of the inserts. This is used for parameters that do not contain creation and deletion dates
        - create_tables (boolean): Whether to create tables at first run
        - query_variant (str): number of the query to generate the parameters
    """
    date_limit_string = date_limit.strftime('%Y-%m-%d')
    date_limit_long = date_limit.timestamp() * 1000
    date_start_long = date_start.timestamp() * 1000
    with open(f"paramgen-queries/pg-{query_variant}.sql", "r") as parameter_query_file:
        parameter_query = parameter_query_file.read().replace(':date_limit_filter', f'\'{date_limit_string}\'')
        parameter_query = parameter_query.replace(':date_limit_long', str(date_limit_long))
        parameter_query = parameter_query.replace(':date_start_long', str(date_start_long))
        if create_tables:
            cursor.execute(f"CREATE TABLE 'Q_{query_variant}' AS SELECT * FROM ({parameter_query});")
        cursor.execute(f"INSERT INTO 'Q_{query_variant}' SELECT * FROM ({parameter_query});")

if __name__ == "__main__":
    parquet_path = "factors/*"
    Path('paramgen.snb.db').unlink(missing_ok=True)

    print("============ Loading the factor tables ============")
    cursor = duckdb.connect(database="paramgen.snb.db")
    directories = glob.glob(f'{parquet_path}')
    print(directories)
    # Create views of raw parquet files
    for directory in directories:
        path_dir = Path(directory)
        if path_dir.is_dir():
            print(path_dir.name)
            print(str(Path(directory).absolute()))
            cursor.execute(
                f"""
                CREATE VIEW {path_dir.name} AS 
                SELECT * FROM read_parquet('{str(Path(directory).absolute()) + "/*.parquet"}');
                """
            )
            # Check tables
            print(cursor.execute(f"SELECT * FROM {path_dir.name} LIMIT 5;").fetch_df().head())

    # Slide through relevant views
    date_limit = datetime(year=2012, month=11, day=29, hour=0, minute=0, second=0)
    date_start = date_limit
    print("Start time of initial_snapshot: " + str(date_limit))
    window_time = timedelta(days=1) # Bucket by week
    print(window_time)
    end_date = datetime(year=2013, month=1, day=1, hour=0, minute=0, second=0)
    # Create factor tables with time_bucket column
    create_tables = True
    while (date_limit < end_date):
        # Create factors
        print(date_limit, end_date, window_time)

        print("============ Generating parameters ============")
        for query_variant in ["1", "2", "3a", "3b", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13a", "13b", "14a", "14b"]:
            print(f"- Q{query_variant}")

            generate_parameters(cursor, date_limit, date_start, create_tables, query_variant)
        create_tables = False
        date_limit = date_limit + window_time
    
    Path('paramgen.snb.db').unlink(missing_ok=True)

    print("============ Output parameters ============")
    for query_variant in ["1", "2", "3a", "3b", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13a", "13b", "14a", "14b"]:
        print(f"- Q{query_variant} TO ../parameters/interactive-{query_variant}.parquet")
        query = remove_duplicates_dict[f"Q_{query_variant}"]
        cursor.execute(query)
        cursor.execute(f"COPY 'Q_{query_variant}' TO '../parameters/interactive-{query_variant}.parquet' WITH (FORMAT PARQUET);")
