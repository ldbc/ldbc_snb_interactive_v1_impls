"""
This script concatenates per entity all part-*.csv files.
"""
import argparse
import glob
from pathlib import Path

path_list = [
    'static/Organisation',
    'static/Place',
    'static/Tag',
    'static/TagClass',
    'dynamic/Comment',
    'dynamic/Comment_hasTag_Tag',
    'dynamic/Forum',
    'dynamic/Forum_hasMember_Person',
    'dynamic/Forum_hasTag_Tag',
    'dynamic/Person',
    'dynamic/Person_hasInterest_Tag',
    'dynamic/Person_knows_Person',
    'dynamic/Person_likes_Comment',
    'dynamic/Person_likes_Post',
    'dynamic/Person_studyAt_University',
    'dynamic/Person_workAt_Company',
    'dynamic/Post',
    'dynamic/Post_hasTag_Tag'
]

def merge_csv_files(path_to_files, output_file):
    """
    Args:
        - path_to_files (str): Path to initial snapshot folder
          e.g. /data/ldbc/initial_snapshot/Entity
    """
    csv_files = glob.glob(f'{path_to_files}/*.csv', recursive=True)
    # This is the one we take the header from
    first_csv_file_path = csv_files.pop() 

    with open(f"{path_to_files}/{output_file}","wb") as csv_new:
        # first file:
        with open(first_csv_file_path, "rb") as f:
            csv_new.write(f.read())
        # now the rest:
        for csv_file in csv_files:
            with open(csv_file, "rb") as f:
                next(f) # skip the header
                csv_new.write(f.read())


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument(
        '--path',
        help="path: Path to the CSV-files",
        type=str,
        required=True
    )
    args = parser.parse_args()

    for entity_path in path_list:
        entity_dir = Path(args.path, entity_path);
        print(entity_dir)
        merge_csv_files(entity_dir, entity_path.split('/')[-1] + '.csv')
