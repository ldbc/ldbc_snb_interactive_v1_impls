#!/bin/bash

# exit upon error
set -eu

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

# PG_CSV_DIR=${PG_CSV_DIR:-$(pwd)/../../../../ldbc_snb_datagen/out/social_network/}
# PG_LOAD_TO_DB=${PG_LOAD_TO_DB:-load} # possible values: 'load', 'skip'

# POSTGRES_DATABASE=${POSTGRES_DATABASE:-ldbcsf1}
# POSTGRES_USER=${POSTGRES_USER:-postgres}

# PG_FORCE_REGENERATE=${PG_FORCE_REGENERATE:-no}
# PG_CREATE_MESSAGE_FILE=${PG_CREATE_MESSAGE_FILE:-no} # possible values: 'no', 'create', 'sort_by_date'

# # $USER is unset on certain systems
# : ${POSTGRES_USER:?"Environment variable POSTGRES_USER is unset or empty"}

echo ===============================================================================
echo Loading data to the Postgres database with the following configuration
echo -------------------------------------------------------------------------------
echo POSTGRES_VERSION: ${POSTGRES_VERSION}
echo POSTGRES_DATA_DIR: ${POSTGRES_DATA_DIR}
echo POSTGRES_SCHEMA_DIR: ${POSTGRES_SCHEMA_DIR}
echo POSTGRES_CONTAINER_NAME: ${POSTGRES_CONTAINER_NAME}
echo POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
echo POSTGRES_DATABASE: ${POSTGRES_DATABASE}
echo POSTGRES_SHARED_MEMORY: ${POSTGRES_SHARED_MEMORY}
echo POSTGRES_USER: ${POSTGRES_USER}
echo ===============================================================================

docker exec -i ${POSTGRES_CONTAINER_NAME} dropdb --if-exists ${POSTGRES_DATABASE} -U ${POSTGRES_USER}
docker exec -i ${POSTGRES_CONTAINER_NAME} createdb ${POSTGRES_DATABASE} -U ${POSTGRES_USER} --template template0 --locale "POSIX"
cat schema-and-load-scripts/schema.sql | docker exec -i ${POSTGRES_CONTAINER_NAME} psql -d ${POSTGRES_DATABASE} -U ${POSTGRES_USER}

echo PGDD ${POSTGRES_DATA_DIR}

cat schema-and-load-scripts/snb-load.sql | \
  sed "s|\${PATHVAR}|/data|g" | \
  sed "s|\${HEADER}|, HEADER, FORMAT csv|g" | \
  sed "s|\${POSTFIX}|.csv|g" | \
  docker exec -i ${POSTGRES_CONTAINER_NAME} psql -d ${POSTGRES_DATABASE} -U ${POSTGRES_USER}

cat schema-and-load-scripts/schema_constraints.sql | docker exec -i ${POSTGRES_CONTAINER_NAME} psql -d ${POSTGRES_DATABASE} -U ${POSTGRES_USER}

# # we regenerate PostgreSQL-specific CSV files for posts and comments, if either
# #  - it doesn't exist
# #  - the source CSV is newer
# #  - we are forced to do so by environment variable PG_FORCE_REGENERATE=yes

# if [ ! -f ${PG_CSV_DIR}/dynamic/post_0_0-postgres.csv -o ${PG_CSV_DIR}/dynamic/post_0_0.csv -nt ${PG_CSV_DIR}/dynamic/post_0_0-postgres.csv -o "${PG_FORCE_REGENERATE}x" = "yesx" ]; then
#   cat ${PG_CSV_DIR}/dynamic/post_0_0.csv | \
#     awk -F '|' '{ print $1"|"$2"|"$3"|"$4"|"$5"|"$6"|"$7"|"$8"|"$9"|"$11"|"$10"|"}' > \
#     ${PG_CSV_DIR}/dynamic/post_0_0-postgres.csv
# fi
# if [ ! -f ${PG_CSV_DIR}/dynamic/comment_0_0-postgres.csv -o ${PG_CSV_DIR}/dynamic/comment_0_0.csv -nt ${PG_CSV_DIR}/dynamic/comment_0_0-postgres.csv -o "${PG_FORCE_REGENERATE}x" = "yesx" ]; then
#   cat ${PG_CSV_DIR}/dynamic/comment_0_0.csv | \
#     awk -F '|' '{print $1"|"$2"||"$3"|"$4"||"$5"|"$6"|"$7"|"$8"||"$9 $10}' > \
#     ${PG_CSV_DIR}/dynamic/comment_0_0-postgres.csv
# fi

# if [ "${PG_CREATE_MESSAGE_FILE}x" != "nox" ]; then
#   if [ ! -f ${PG_CSV_DIR}/message_0_0-postgres.csv -o ${PG_CSV_DIR}/post_0_0-postgres.csv -nt ${PG_CSV_DIR}/message_0_0-postgres.csv -o $PG_DATA_DIR/comment_0_0-postgres.csv -nt ${PG_CSV_DIR}/message_0_0-postgres.csv -o "${PG_FORCE_REGENERATE}x" = "yesx" ] ; then
#     # create CSV file header
#     head -n 1 ${PG_CSV_DIR}/post_0_0-postgres.csv | sed -e 's/$/replyOfPostreplyOfComment/' >${PG_CSV_DIR}/message_0_0-postgres.csv

#     if [ "${PG_CREATE_MESSAGE_FILE}x" = "sort_by_datex" ]; then
#       sortExec='sort -t| -k3'
#     else
#       # we just pipe data untouched
#       sortExec=cat
#     fi

#     cat <(tail -n +2 ${PG_CSV_DIR}/post_0_0-postgres.csv) <(tail -n +2 ${PG_CSV_DIR}/comment_0_0-postgres.csv) | $sortExec >>$PG_DATA_DIR/message_0_0-postgres.csv
#   fi
# fi

# # TODO: revise, cleanup
# if [ "${PG_LOAD_TO_DB}x" = "loadx" ]; then
#   # /usr/bin/dropdb --if-exists ${POSTGRES_DATABASE} -U ${POSTGRES_USER} -p ${PG_PORT}
#   docker exec -i ${POSTGRES_CONTAINER_NAME} dropdb --if-exists ${POSTGRES_DATABASE} -U ${POSTGRES_USER}
#   # /usr/bin/createdb ${POSTGRES_DATABASE} -U ${POSTGRES_USER} -p ${PG_PORT} --template template0 -l "C"
#   docker exec -i ${POSTGRES_CONTAINER_NAME} createdb ${POSTGRES_DATABASE} -U ${POSTGRES_USER} --template template0 --locale "POSIX"
#   # /usr/bin/psql -d ${POSTGRES_DATABASE} -U ${POSTGRES_USER} -p ${PG_PORT} -a -f schema.sql
#   cat schema.sql | docker exec -i ${POSTGRES_CONTAINER_NAME} psql -d ${POSTGRES_DATABASE} -U ${POSTGRES_USER}
#   # (cat snb-load.sql | sed "s|PATHVAR|${PG_CSV_DIR}|g"; echo "\q\n") | /usr/bin/psql -d ${POSTGRES_DATABASE} -U ${POSTGRES_USER} -p ${PG_PORT}
#   (cat snb-load.sql | sed "s|PATHVAR|/data|g"; echo "\q\n") | docker exec -i ${POSTGRES_CONTAINER_NAME} psql -U postgres
#   # /usr/bin/psql -d ${POSTGRES_DATABASE} -U ${POSTGRES_USER} -p ${PG_PORT} -a -f schema_constraints.sql
#   cat schema_constraints.sql | docker exec -i ${POSTGRES_CONTAINER_NAME} psql -d ${POSTGRES_DATABASE} -U ${POSTGRES_USER}
# fi
