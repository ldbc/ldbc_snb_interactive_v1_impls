import argparse
import json
import os
import subprocess
import sys
from pathlib import Path
import time
from timeit import default_timer as timer
import ast
import requests
import re
from datetime import datetime, timedelta
from random import randrange, choice
from glob import glob

STATIC_NAMES = [
    'Organisation',
    'Organisation_isLocatedIn_Place',
    'Place',
    'Place_isPartOf_Place',
    'Tag',
    'TagClass',
    'TagClass_isSubclassOf_TagClass',
    'Tag_hasType_TagClass',
]
DYNAMIC_VERTICES = [
    'Comment',
    'Forum',
    'Person',
    'Post',
]
DYNAMIC_EDGES = [
    'Comment_hasCreator_Person',
    'Comment_hasTag_Tag',
    'Comment_isLocatedIn_Country',
    'Comment_replyOf_Comment',
    'Comment_replyOf_Post',
    'Forum_containerOf_Post',
    'Forum_hasMember_Person',
    'Forum_hasModerator_Person',
    'Forum_hasTag_Tag',
    'Person_hasInterest_Tag',
    'Person_isLocatedIn_City',
    'Person_knows_Person',
    'Person_likes_Comment',
    'Person_likes_Post',
    'Person_studyAt_University',
    'Person_workAt_Company',
    'Post_hasCreator_Person',
    'Post_hasTag_Tag',
    'Post_isLocatedIn_Country',
]
DYNAMIC_NAMES = DYNAMIC_VERTICES + DYNAMIC_EDGES


def load_data(job, machine, data_dir, tag, names, suffix, date = None, interactive=False):
    def ChangeName(name):
        entities = name.split('_')
        if len(entities) != 3: return name.lower()
        entities[0] = entities[0].lower()
        entities[2] = entities[2].lower()
        if entities[2] in ['country', 'city']:
            entities[2] = 'place'
        if entities[2] in ['university', 'company']:
            entities[2] = 'organisation'
        return '_'.join(entities)
    file_paths = [f'{data_dir}/{tag}/{ChangeName(name)}' for name in names]
    print(file_paths)

    # XXX
    names = ['ANY:social_network/static/organisation_0_0']

    gsql = f'RUN LOADING JOB {job} USING '
    if suffix and '.' not in suffix: suffix = '.' + suffix

    gsql += ', '.join(f'file_{name}="{machine}{file_path}{suffix}"' for name, file_path in zip(names, file_paths))
    print(gsql)
    print(f"subprocess.run('gsql -g LDBC_SNB \'{gsql}\'', shell=True)")
    #subprocess.run(f'gsql -g LDBC_SNB \'{gsql}\'', shell=True)

    gsql += ', '.join(f'file_{name}="{machine}{file_path}{suffix}"' for name, file_path in zip(names, file_paths))
    print(gsql)
#     subprocess.run(f'gsql -g LDBC_SNB \'{gsql}\'', shell=True)

if __name__ == '__main__':
    load_data('load_static_with_header', "ANY:", "../datagen/social_network", 'static', STATIC_NAMES, '_0_0.csv', interactive=True)