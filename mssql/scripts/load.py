import time
import pandas as pd
import base64
import os
from DBLoader import DBLoader

"""
FILE: load.py

DESC: Script to load the LDBC SNB interactive data into SQL Server.
      The sql scripts executed in this script can be found under
      the ddl/ directory. 

      This script encodes the UTF-8 columns to UTF-16LE and then
      base64 which is decoded in SQL Server to perserve the unicode
      characters and speedup the loading. (Bulk loading unicode
      is not supported by SQL Server on Linux as of 05-2022) The 
      script used on SQL Server converts it to UTF-16LE, the 
      encoding used in the NVARCHAR field, using XML. Therefore,
      XML characters are encoded here as well.

      Datetime information is loaded and written back to a format
      that is compatible by SQL Servers datetimeoffset datatype.
"""

def get_df_from_csv(csv_file) -> pd.DataFrame:
    """
    Load dataframe from csv
    Args:
        - csv_file (str): csv-filename
    Returns:
        Dataframe with loaded CSV-data.
    """
    filepath = os.path.join('/data/', csv_file).replace("\\","/")
    chunksize = 10 ** 6
    df_list = []
    with pd.read_csv(filepath, chunksize=chunksize, sep='|', dtype=str, na_filter=False) as reader:
        for chunk in reader:
            df_list.append(chunk)
    return pd.concat(df_list)


def convert(csv_file, columns):
    """
    Convert unicode sensitive columns to base64 and datetime strings.
    Args:
        - csv_file (str): Path to the CSV file
        - columns (list(list)): list containing a list of unicode columns at [0]
                                and datetime columns at [1].
    Returns:
        None (files are written to disk)
    """
    df = get_df_from_csv(csv_file)
    print(f"Parsing file: {csv_file}")
    for column in columns[0]:
        # Convert unicode columns
        df.fillna('', inplace=True)
        # We need to replace these characters since we use XML conversion
        df[column] = df[column].str.replace("&", "&amp;")
        df[column] = df[column].str.replace("<", "&lt;")
        df[column] = df[column].str.replace(">", "&gt;")
        df[column] = df[column].str.replace('"', "&quot;")
        df[column] = df[column].str.replace("'", "&apos;")
        df[column] = df[column].str.encode('utf-16-le', 'strict').apply(base64.b64encode)
        df[column] = df[column].str.decode('ascii') # to remove the b'' in the string

    for column in columns[1]:
        # convert datetime columns
        df[column]= pd.to_datetime(df[column])

    filepath_new = os.path.join('/data/', csv_file + "_encoded.csv").replace("\\","/")
    df.to_csv(filepath_new, sep='|', index=False)


def encode_columns():
    csv_dict = {
        "dynamic/post_0_0.csv":[['content'], ['creationDate']],
        "dynamic/comment_0_0.csv":[['content'], ['creationDate']],
        "dynamic/forum_0_0.csv":[['title'], ['creationDate']],
        "static/organisation_0_0.csv":[['name'], []],
        "dynamic/person_0_0.csv":[['firstName','lastName'], ['creationDate']],
        "static/place_0_0.csv":[['name'], []],
        "static/tagclass_0_0.csv":[['name'], []],
        "static/tag_0_0.csv":[['name'], []],
        "dynamic/person_likes_post_0_0.csv":[[], ['creationDate']],
        "dynamic/person_likes_comment_0_0.csv":[[], ['creationDate']],
        "dynamic/forum_hasMember_person_0_0.csv":[[], ['joinDate']],
        "dynamic/person_knows_person_0_0.csv":[[], ['creationDate']]
    }

    for filename, columns in csv_dict.items():
        convert(filename, columns)

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
        start_encode = time.time()
        encode_columns()
        end_encode = time.time()
        duration = end_encode - start_encode
        print(f"-> {duration:.4f} seconds")

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
