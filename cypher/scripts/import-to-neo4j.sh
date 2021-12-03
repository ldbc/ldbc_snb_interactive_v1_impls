#!/bin/bash

set -e
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

: ${NEO4J_CONTAINER_ROOT:?"Environment variable NEO4J_CONTAINER_ROOT is unset or empty"}
: ${NEO4J_DATA_DIR:?"Environment variable NEO4J_DATA_DIR is unset or empty"}
: ${NEO4J_CONVERTED_CSV_DIR:?"Environment variable NEO4J_CONVERTED_CSV_DIR is unset or empty"}
: ${NEO4J_CSV_POSTFIX:?"Environment variable NEO4J_CSV_POSTFIX is unset or empty"}
: ${NEO4J_VERSION:?"Environment variable NEO4J_VERSION is unset or empty"}

# make sure directories exist
mkdir -p ${NEO4J_CONTAINER_ROOT}/{logs,import,plugins}

# start with a fresh data dir (required by the CSV importer)
mkdir -p ${NEO4J_DATA_DIR}
rm -rf ${NEO4J_DATA_DIR}/*

docker run --rm \
    --user="$(id -u):$(id -g)" \
    --publish=7474:7474 \
    --publish=7687:7687 \
    --volume=${NEO4J_DATA_DIR}:/data:z \
    --volume=${NEO4J_CONVERTED_CSV_DIR}:/import:z \
    --volume=${NEO4J_CONTAINER_ROOT}/logs:/logs:z \
    --volume=${NEO4J_CONTAINER_ROOT}/import:/var/lib/neo4j/import:z \
    --volume=${NEO4J_CONTAINER_ROOT}/plugins:/plugins:z \
    ${NEO4J_ENV_VARS} \
    neo4j:${NEO4J_VERSION} \
    neo4j-admin import \
    --id-type=INTEGER \
    --nodes=Place="/import/static/place${NEO4J_CSV_POSTFIX}" \
    --nodes=Organisation="/import/static/organisation${NEO4J_CSV_POSTFIX}" \
    --nodes=TagClass="/import/static/tagclass${NEO4J_CSV_POSTFIX}" \
    --nodes=Tag="/import/static/tag${NEO4J_CSV_POSTFIX}" \
    --nodes=Comment:Message="/import/dynamic/comment${NEO4J_CSV_POSTFIX}" \
    --nodes=Forum="/import/dynamic/forum${NEO4J_CSV_POSTFIX}" \
    --nodes=Person="/import/dynamic/person${NEO4J_CSV_POSTFIX}" \
    --nodes=Post:Message="/import/dynamic/post${NEO4J_CSV_POSTFIX}" \
    --relationships=IS_PART_OF="/import/static/place_isPartOf_place${NEO4J_CSV_POSTFIX}" \
    --relationships=IS_SUBCLASS_OF="/import/static/tagclass_isSubclassOf_tagclass${NEO4J_CSV_POSTFIX}" \
    --relationships=IS_LOCATED_IN="/import/static/organisation_isLocatedIn_place${NEO4J_CSV_POSTFIX}" \
    --relationships=HAS_TYPE="/import/static/tag_hasType_tagclass${NEO4J_CSV_POSTFIX}" \
    --relationships=HAS_CREATOR="/import/dynamic/comment_hasCreator_person${NEO4J_CSV_POSTFIX}" \
    --relationships=IS_LOCATED_IN="/import/dynamic/comment_isLocatedIn_place${NEO4J_CSV_POSTFIX}" \
    --relationships=REPLY_OF="/import/dynamic/comment_replyOf_comment${NEO4J_CSV_POSTFIX}" \
    --relationships=REPLY_OF="/import/dynamic/comment_replyOf_post${NEO4J_CSV_POSTFIX}" \
    --relationships=CONTAINER_OF="/import/dynamic/forum_containerOf_post${NEO4J_CSV_POSTFIX}" \
    --relationships=HAS_MEMBER="/import/dynamic/forum_hasMember_person${NEO4J_CSV_POSTFIX}" \
    --relationships=HAS_MODERATOR="/import/dynamic/forum_hasModerator_person${NEO4J_CSV_POSTFIX}" \
    --relationships=HAS_TAG="/import/dynamic/forum_hasTag_tag${NEO4J_CSV_POSTFIX}" \
    --relationships=HAS_INTEREST="/import/dynamic/person_hasInterest_tag${NEO4J_CSV_POSTFIX}" \
    --relationships=IS_LOCATED_IN="/import/dynamic/person_isLocatedIn_place${NEO4J_CSV_POSTFIX}" \
    --relationships=KNOWS="/import/dynamic/person_knows_person${NEO4J_CSV_POSTFIX}" \
    --relationships=LIKES="/import/dynamic/person_likes_comment${NEO4J_CSV_POSTFIX}" \
    --relationships=LIKES="/import/dynamic/person_likes_post${NEO4J_CSV_POSTFIX}" \
    --relationships=HAS_CREATOR="/import/dynamic/post_hasCreator_person${NEO4J_CSV_POSTFIX}" \
    --relationships=HAS_TAG="/import/dynamic/comment_hasTag_tag${NEO4J_CSV_POSTFIX}" \
    --relationships=HAS_TAG="/import/dynamic/post_hasTag_tag${NEO4J_CSV_POSTFIX}" \
    --relationships=IS_LOCATED_IN="/import/dynamic/post_isLocatedIn_place${NEO4J_CSV_POSTFIX}" \
    --relationships=STUDY_AT="/import/dynamic/person_studyAt_organisation${NEO4J_CSV_POSTFIX}" \
    --relationships=WORK_AT="/import/dynamic/person_workAt_organisation${NEO4J_CSV_POSTFIX}" \
    --delimiter '|'
