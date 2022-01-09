static_file_names_1 = ['organisation', 'place', 'tagclass', 'tagclass_isSubclassOf_tagclass', 'tag', 'tag_hasType_tagclass']
static_file_names_2 = ['organisation_isLocatedIn_place', 'place_isPartOf_place']

suffix = '_0_0.csv'
machine = 'ANY:'
data_dir = 'social_network'
tag = 'static'
graph = 'LDBC_SNB'
static_job_files = {
    'load_static_1': static_file_names_1,
    'load_static_2': static_file_names_2
}

for job, names in static_job_files.items():
    # We are leveraging the naming convention: DEFINE FILENAME name is the same as a generated file name (without _0_0.csv)
    file_defs = ', '.join(f'{name}="{machine}{data_dir}/{tag}/{name}{suffix}"' for name in names)
    gsql_statement = f'RUN LOADING JOB {job} USING ' + file_defs

    print(gsql_statement)