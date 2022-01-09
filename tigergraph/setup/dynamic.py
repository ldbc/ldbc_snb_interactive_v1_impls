dynamic_file_names_1 = ['comment', 'comment_hasCreator_person', 'comment_hasTag_tag', 'comment_isLocatedIn_place',
    'comment_replyOf_comment', 'comment_replyOf_post', 'forum', 'forum_containerOf_post', 'forum_hasMember_person',
    'forum_hasModerator_person', 'forum_hasTag_tag', 'person', 'person_hasInterest_tag', 'person_isLocatedIn_place',
    'person_knows_person', 'person_likes_comment', 'person_likes_post', 'person_studyAt_organisation', 'person_workAt_organisation',
    'post', 'post_hasCreator_person', 'post_hasTag_tag', 'post_isLocatedIn_place']

suffix = '_0_0.csv'
machine = 'ANY:'
data_dir = 'social_network'
tag = 'dynamic'
graph = 'LDBC_SNB'

dynamic_job_files = {
    'load_dynamic_1': dynamic_file_names_1
    #,'load_dynamic_2': dynamic_file_names_2
}

for job, names in dynamic_job_files.items():
    # We are leveraging the naming convention: DEFINE FILENAME name is the same as a generated file name
    file_defs = ', '.join(f'{name}="{machine}{data_dir}/{tag}/{name}{suffix}"' for name in names)
    gsql_statement = f'RUN LOADING JOB {job} USING ' + file_defs

    print(gsql_statement)