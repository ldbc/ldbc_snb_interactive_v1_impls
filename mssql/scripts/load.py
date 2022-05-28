import time
import pandas as pd
import base64
import os
from DBLoader import DBLoader

def get_df_from_csv(csv_file) -> pd.DataFrame:
    """
    Load dataframe from csv
    Args:
        - csv_file (str): csv-filename
    """
    filepath = os.path.join('/data/', csv_file).replace("\\","/")
    chunksize = 10 ** 6
    df_list = []
    with pd.read_csv(filepath, chunksize=chunksize, sep='|', dtype=str, na_filter=False) as reader:
        for chunk in reader:
            df_list.append(chunk)
    return pd.concat(df_list)


def convert_to_base64(csv_file, columns):
    df = get_df_from_csv(csv_file)

    print(df[columns].head())

    for column in columns:
        df.fillna('', inplace=True)
        # We need to replace these characters since we use XML conversion in the server
        df[column] = df[column].str.replace("&", "&amp;")
        df[column] = df[column].str.replace("<", "&lt;")
        df[column] = df[column].str.replace(">", "&gt;")
        df[column] = df[column].str.replace('"', "&quot;")
        df[column] = df[column].str.replace("'", "&apos;")
        df[column] = df[column].str.encode('utf-8', 'strict').apply(base64.b64encode)
        df[column] = df[column].str.decode('ascii')# to remove the b''

    filepath_new = os.path.join('/data/', csv_file + "_encoded.csv").replace("\\","/")
    df.to_csv(filepath_new, sep='|', index=False)


def encode_columns():
    csv_dict = {
        "dynamic/post_0_0.csv":['content'],
        "dynamic/comment_0_0.csv":['content'],
        "dynamic/forum_0_0.csv":['title'],
        "static/organisation_0_0.csv":['name'],
        "dynamic/person_0_0.csv":['firstName','lastName'],
        "static/place_0_0.csv":['name'],
        "static/tagclass_0_0.csv":['name'],
        "static/tag_0_0.csv":['name']
    }

    for filename, columns in csv_dict.items():
        convert_to_base64(filename, columns)

if __name__ == "__main__":
    # Fetch the env variables.
    start_total = time.time()
    DB_PORT = os.getenv("MSSQL_PORT", 1433)
    DB_NAME = os.getenv("MSSQL_DB_NAME", "ldbc")
    DB_USER = os.getenv("MSSQL_USER", "SA")
    DB_PASS = os.getenv("MSSQL_PASSWORD", "")
    RECREATE = os.getenv("MSSQL_RECREATE", True)
    SERVER_NAME = os.getenv("MSSQL_SERVER_NAME", "snb-interactive-mssql")
    DB_DRIVER = os.getenv("MSSQL_DRIVER", "ODBC Driver 18 for SQL Server")

    DBL = DBLoader(SERVER_NAME, DB_NAME, DB_USER, DB_PASS, DB_PORT, DB_DRIVER)
    DBL.check_and_create_database(DB_NAME, RECREATE)
    if (RECREATE):
        print("Create tables")
        DBL.run_ddl_scripts("ddl/schema.sql")

        print("Encode UTF-8 columns to Base64")
        encode_columns()

        print("Load initial snapshot")
        DBL.run_ddl_scripts("ddl/load.sql")

        print("Convert UTF-8")
        DBL.run_ddl_scripts("ddl/unicode.sql")

        print("Creating materialized views . . . ")
        DBL.run_ddl_scripts("ddl/schema_constraints.sql")

    end_total = time.time()
    duration_total = end_total - start_total
    print("Loaded initial snapshot to SQL Server.")
    print(f"-> {duration_total:.4f} seconds")
