#!/bin/bash

set -e
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

: ${NEO4J_CONTAINER_ROOT:?"Environment variable NEO4J_CONTAINER_ROOT is unset or empty"}
: ${NEO4J_DATA_DIR:?"Environment variable NEO4J_DATA_DIR is unset or empty"}
: ${NEO4J_CSV_DIR:?"Environment variable NEO4J_CSV_DIR is unset or empty"}
: ${NEO4J_VERSION:?"Environment variable NEO4J_VERSION is unset or empty"}
: ${NEO4J_CONTAINER_NAME:?"Environment variable NEO4J_CONTAINER_NAME is unset or empty"}

# make sure directories exist
mkdir -p ${NEO4J_CONTAINER_ROOT}/{logs,import,plugins}

# start with a fresh data dir (required by the CSV importer)
mkdir -p ${NEO4J_DATA_DIR}
rm -rf ${NEO4J_DATA_DIR}/*

docker run --rm \
    --user="$(id -u):$(id -g)" \
    --publish=7474:7474 \
    --publish=7687:7687 \
    --volume=${NEO4J_DATA_DIR}:/data \
    --volume=${NEO4J_CSV_DIR}:/import \
    ${NEO4J_ENV_VARS} \
    neo4j:${NEO4J_VERSION} \
    neo4j-admin import \
    --id-type=INTEGER \
    --nodes=Place="/import/static/Place${NEO4J_CSV_POSTFIX}" \
    --nodes=Organisation="/import/static/Organisation${NEO4J_CSV_POSTFIX}" \
    --nodes=TagClass="/import/static/TagClass${NEO4J_CSV_POSTFIX}" \
    --nodes=Tag="/import/static/Tag${NEO4J_CSV_POSTFIX}" \
    --nodes=Forum="/import/dynamic/Forum${NEO4J_CSV_POSTFIX}" \
    --nodes=Person="/import/dynamic/Person${NEO4J_CSV_POSTFIX}" \
    --nodes=Message:Comment="/import/dynamic/Comment${NEO4J_CSV_POSTFIX}" \
    --nodes=Message:Post="/import/dynamic/Post${NEO4J_CSV_POSTFIX}" \
    --relationships=IS_PART_OF="/import/static/Place_isPartOf_Place${NEO4J_CSV_POSTFIX}" \
    --relationships=IS_SUBCLASS_OF="/import/static/TagClass_isSubclassOf_TagClass${NEO4J_CSV_POSTFIX}" \
    --relationships=IS_LOCATED_IN="/import/static/Organisation_isLocatedIn_Place${NEO4J_CSV_POSTFIX}" \
    --relationships=HAS_TYPE="/import/static/Tag_hasType_TagClass${NEO4J_CSV_POSTFIX}" \
    --relationships=HAS_CREATOR="/import/dynamic/Comment_hasCreator_Person${NEO4J_CSV_POSTFIX}" \
    --relationships=IS_LOCATED_IN="/import/dynamic/Comment_isLocatedIn_Country${NEO4J_CSV_POSTFIX}" \
    --relationships=REPLY_OF="/import/dynamic/Comment_replyOf_Comment${NEO4J_CSV_POSTFIX}" \
    --relationships=REPLY_OF="/import/dynamic/Comment_replyOf_Post${NEO4J_CSV_POSTFIX}" \
    --relationships=CONTAINER_OF="/import/dynamic/Forum_containerOf_Post${NEO4J_CSV_POSTFIX}" \
    --relationships=HAS_MEMBER="/import/dynamic/Forum_hasMember_Person${NEO4J_CSV_POSTFIX}" \
    --relationships=HAS_MODERATOR="/import/dynamic/Forum_hasModerator_Person${NEO4J_CSV_POSTFIX}" \
    --relationships=HAS_TAG="/import/dynamic/Forum_hasTag_Tag${NEO4J_CSV_POSTFIX}" \
    --relationships=HAS_INTEREST="/import/dynamic/Person_hasInterest_Tag${NEO4J_CSV_POSTFIX}" \
    --relationships=IS_LOCATED_IN="/import/dynamic/Person_isLocatedIn_City${NEO4J_CSV_POSTFIX}" \
    --relationships=KNOWS="/import/dynamic/Person_knows_Person${NEO4J_CSV_POSTFIX}" \
    --relationships=LIKES="/import/dynamic/Person_likes_Comment${NEO4J_CSV_POSTFIX}" \
    --relationships=LIKES="/import/dynamic/Person_likes_Post${NEO4J_CSV_POSTFIX}" \
    --relationships=HAS_CREATOR="/import/dynamic/Post_hasCreator_Person${NEO4J_CSV_POSTFIX}" \
    --relationships=HAS_TAG="/import/dynamic/Comment_hasTag_Tag${NEO4J_CSV_POSTFIX}" \
    --relationships=HAS_TAG="/import/dynamic/Post_hasTag_Tag${NEO4J_CSV_POSTFIX}" \
    --relationships=IS_LOCATED_IN="/import/dynamic/Post_isLocatedIn_Country${NEO4J_CSV_POSTFIX}" \
    --relationships=STUDY_AT="/import/dynamic/Person_studyAt_University${NEO4J_CSV_POSTFIX}" \
    --relationships=WORK_AT="/import/dynamic/Person_workAt_Company${NEO4J_CSV_POSTFIX}" \
    --delimiter '|'
