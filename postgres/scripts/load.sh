#!/bin/bash

# exit upon error
set -e

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

PG_CSV_DIR=${PG_CSV_DIR:-$(pwd)/../../../../ldbc_snb_datagen/out/social_network/}
PG_LOAD_TO_DB=${PG_LOAD_TO_DB:-load} # possible values: 'load', 'skip'
PG_DB_NAME=${PG_DB_NAME:-ldbcsf1}
PG_USER=${PG_USER:-$USER}

PG_FORCE_REGENERATE=${PG_FORCE_REGENERATE:-no}
PG_CREATE_MESSAGE_FILE=${PG_CREATE_MESSAGE_FILE:-no} # possible values: 'no', 'create', 'sort_by_date'

# $USER is unset on certain systems
: ${PG_USER:?"Environment variable PG_USER is unset or empty"}

echo ===============================================================================
echo Loading data to the Postgres database with the following configuration
echo -------------------------------------------------------------------------------
echo PG_CSV_DIR: ${PG_CSV_DIR}
echo PG_LOAD_TO_DB: ${PG_LOAD_TO_DB}
echo PG_DB_NAME: ${PG_DB_NAME}
echo PG_USER: ${PG_USER}
echo PG_FORCE_REGENERATE: ${PG_FORCE_REGENERATE}
echo PG_CREATE_MESSAGE_FILE: ${PG_CREATE_MESSAGE_FILE}
echo ===============================================================================

# we regenerate PostgreSQL-specific CSV files for posts and comments, if either
#  - it doesn't exist
#  - the source CSV is newer
#  - we are forced to do so by environment variable PG_FORCE_REGENERATE=yes

if [ ! -f ${PG_CSV_DIR}/dynamic/post_0_0-postgres.csv -o ${PG_CSV_DIR}/dynamic/post_0_0.csv -nt ${PG_CSV_DIR}/dynamic/post_0_0-postgres.csv -o "${PG_FORCE_REGENERATE}x" = "yesx" ]; then
  cat ${PG_CSV_DIR}/dynamic/post_0_0.csv | \
    awk -F '|' '{ print $1"|"$2"|"$3"|"$4"|"$5"|"$6"|"$7"|"$8"|"$9"|"$11"|"$10"|"}' > \
    ${PG_CSV_DIR}/dynamic/post_0_0-postgres.csv
fi
if [ ! -f ${PG_CSV_DIR}/dynamic/comment_0_0-postgres.csv -o ${PG_CSV_DIR}/dynamic/comment_0_0.csv -nt ${PG_CSV_DIR}/dynamic/comment_0_0-postgres.csv -o "${PG_FORCE_REGENERATE}x" = "yesx" ]; then
  cat ${PG_CSV_DIR}/dynamic/comment_0_0.csv | \
    awk -F '|' '{print $1"|"$2"||"$3"|"$4"||"$5"|"$6"|"$7"|"$8"||"$9 $10}' > \
    ${PG_CSV_DIR}/dynamic/comment_0_0-postgres.csv
fi

if [ "${PG_CREATE_MESSAGE_FILE}x" != "nox" ]; then
  if [ ! -f ${PG_CSV_DIR}/message_0_0-postgres.csv -o ${PG_CSV_DIR}/post_0_0-postgres.csv -nt ${PG_CSV_DIR}/message_0_0-postgres.csv -o $PG_DATA_DIR/comment_0_0-postgres.csv -nt ${PG_CSV_DIR}/message_0_0-postgres.csv -o "${PG_FORCE_REGENERATE}x" = "yesx" ] ; then
    # create CSV file header
    head -n 1 ${PG_CSV_DIR}/post_0_0-postgres.csv | sed -e 's/$/replyOfPostreplyOfComment/' >${PG_CSV_DIR}/message_0_0-postgres.csv

    if [ "${PG_CREATE_MESSAGE_FILE}x" = "sort_by_datex" ]; then
      sortExec='sort -t| -k3'
    else
      # we just pipe data untouched
      sortExec=cat
    fi

    cat <(tail -n +2 ${PG_CSV_DIR}/post_0_0-postgres.csv) <(tail -n +2 ${PG_CSV_DIR}/comment_0_0-postgres.csv) | $sortExec >>$PG_DATA_DIR/message_0_0-postgres.csv
  fi
fi

# TODO: revise, cleanup
if [ "${PG_LOAD_TO_DB}x" = "loadx" ]; then
  # /usr/bin/dropdb --if-exists ${PG_DB_NAME} -U ${PG_USER} -p ${PG_PORT}
  docker exec -i ${POSTGRES_CONTAINER_NAME} dropdb --if-exists ${PG_DB_NAME} -U ${PG_USER}
  # /usr/bin/createdb ${PG_DB_NAME} -U ${PG_USER} -p ${PG_PORT} --template template0 -l "C"
  docker exec -i ${POSTGRES_CONTAINER_NAME} createdb ${PG_DB_NAME} -U ${PG_USER} --template template0 -l "C"
  # /usr/bin/psql -d ${PG_DB_NAME} -U ${PG_USER} -p ${PG_PORT} -a -f schema.sql
  cat schema.sql | docker exec -i ${POSTGRES_CONTAINER_NAME} psql -d ${PG_DB_NAME} -U ${PG_USER}
  # (cat snb-load.sql | sed "s|PATHVAR|${PG_CSV_DIR}|g"; echo "\q\n") | /usr/bin/psql -d ${PG_DB_NAME} -U ${PG_USER} -p ${PG_PORT}
  (cat snb-load.sql | sed "s|PATHVAR|/data|g"; echo "\q\n") | docker exec -i ${POSTGRES_CONTAINER_NAME} psql -U postgres
  # /usr/bin/psql -d ${PG_DB_NAME} -U ${PG_USER} -p ${PG_PORT} -a -f schema_constraints.sql
  cat schema_constraints.sql | docker exec -i ${POSTGRES_CONTAINER_NAME} psql -d ${PG_DB_NAME} -U ${PG_USER}
fi
