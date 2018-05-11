#!/bin/bash

# exit upon non-zero pipe (roughly: command) status
set -e

QUERY_NUMBER=$1
DBTOASTER_BIN=${DBTOASTER_BIN:-$HOME/bin/dbtoaster/bin/dbtoaster}

if [ "${QUERY_NUMBER}x" == "x" ]; then
  echo 1>&2 "ERROR: No query number was provided. Provide bi query number as first argument, e.g. '$0 1'"
  exit -2
fi

TMP_SQL_SCRIPT=$(mktemp)

cat >$TMP_SQL_SCRIPT <<HERE
INCLUDE 'schema.sql';

INCLUDE 'queries/bi/query${QUERY_NUMBER}.sql';
HERE

${DBTOASTER_BIN} -l scala -r $TMP_SQL_SCRIPT

# clean up temp script
rm $TMP_SQL_SCRIPT
