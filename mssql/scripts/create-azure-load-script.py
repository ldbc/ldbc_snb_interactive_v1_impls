"""
Creates a load-azure-files.sql file based on the load-azure.sql file
with the given storage folder path and storage account name.

The resulting script is used to load LDBC data into SQL Server from a storage account.
Note that this script requires merged csv files (created with the merge_csvs.py script)

Usage:
python3 create-azure-load-script.py \
    --path ../ddl/ \
    --storage_folder ldbc_snb_interactive_sf1/composite-merged-fk/ \
    --storage_endpoint https://data.blob.core.windows.net/container/ \
    --sas_token 'SAS_TOKEN' \
    --master_key ASecretPassword
"""

import argparse
from pathlib import Path
import re
import os

entity_to_csv_file= {
    ":organisation_csv"                 : "initial_snapshot/static/Organisation/Organisation.csv",
    ":place_csv"                        : "initial_snapshot/static/Place/Place.csv",
    ":tag_csv"                          : "initial_snapshot/static/Tag/Tag.csv",
    ":tagclass_csv"                     : "initial_snapshot/static/TagClass/TagClass.csv",
    ":comment_csv"                      : "initial_snapshot/dynamic/Comment/Comment.csv",
    ":comment_hastag_tag_csv"           : "initial_snapshot/dynamic/Comment_hasTag_Tag/Comment_hasTag_Tag.csv",
    ":forum_csv"                        : "initial_snapshot/dynamic/Forum/Forum.csv",
    ":forum_hasmember_person_csv"       : "initial_snapshot/dynamic/Forum_hasMember_Person/Forum_hasMember_Person.csv",
    ":forum_hastag_tag_csv"             : "initial_snapshot/dynamic/Forum_hasTag_Tag/Forum_hasTag_Tag.csv",
    ":person_csv"                       : "initial_snapshot/dynamic/Person/Person.csv",
    ":person_hasinterest_tag_csv"       : "initial_snapshot/dynamic/Person_hasInterest_Tag/Person_hasInterest_Tag.csv",
    ":person_knows_person_csv"          : "initial_snapshot/dynamic/Person_knows_Person/Person_knows_Person.csv",
    ":person_likes_comment_csv"         : "initial_snapshot/dynamic/Person_likes_Comment/Person_likes_Comment.csv",
    ":person_likes_post_csv"            : "initial_snapshot/dynamic/Person_likes_Post/Person_likes_Post.csv",
    ":person_studyat_university_csv"    : "initial_snapshot/dynamic/Person_studyAt_University/Person_studyAt_University.csv",
    ":person_workat_company_csv"        : "initial_snapshot/dynamic/Person_workAt_Company/Person_workAt_Company.csv",
    ":post_csv"                         : "initial_snapshot/dynamic/Post/Post.csv",
    ":post_hastag_tag_csv"              : "initial_snapshot/dynamic/Post_hasTag_Tag/Post_hasTag_Tag.csv"
}

def create_azure_load_script(sql_path, storage_folder, endpoint, sas_token, master_key):
    with open(Path(sql_path, 'load-azure.sql' ), "rt") as fin:
        with open(Path(sql_path, 'load-azure-temp.sql' ), "wt") as fout:
            for line in fin:
                new_line = line.replace(r':azure_storage_sas_token', sas_token)
                new_line = new_line.replace(r':azure_storage_endpoint', endpoint)
                new_line = new_line.replace(r':master_key_password', master_key)
                fout.write(new_line)

    with open(Path(sql_path, 'load-azure-temp.sql' ) , "r") as f:
        with open(Path(sql_path, 'load-azure-files.sql' ), "w") as w:
            queries_file = f.read()
            queries = queries_file.split(";")
            for query in queries:
                if query.isspace():
                    continue
                print(query)
                m = re.search(':.*csv', query)
                if(m):
                    csv_key = m.group(0)
                    query_new = query.replace(csv_key, str(Path(storage_folder, entity_to_csv_file[csv_key])))
                    w.write(query_new + ';')
                else:
                    w.write(query + ';')
            w.write('\n')

    os.remove(Path(sql_path, 'load-azure-temp.sql' ))


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument(
        '--path',
        help="path_to_load_script: Path to the load-azure.sql script",
        type=str,
        required=True
    )
    parser.add_argument(
        '--storage_folder',
        help="storage_folder: Path of the folder where the 'initial_snapshot' is located, e.g. /data/sf1/",
        type=str,
        required=True
    )
    parser.add_argument(
        '--storage_endpoint',
        help="storage_endpoint: URL of the storage endpoint including container name, e.g. https://data.blob.core.windows.net/container",
        type=str,
        required=True
    )
    parser.add_argument(
        '--sas_token',
        help="sas_token: Shared Access Signature token to acccess the storage account",
        type=str,
        required=True
    )
    parser.add_argument(
        '--master_key',
        help="master_key: Shared Access Signature token to acccess the storage account",
        type=str,
        required=True
    )
    args = parser.parse_args()
    create_azure_load_script(args.path, args.storage_folder, args.storage_endpoint, args.sas_token, args.master_key)
