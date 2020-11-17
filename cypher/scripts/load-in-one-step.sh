#!/bin/bash

set -e

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

echo ===============================================================================
echo Loading the Neo4j database with the following parameters
echo -------------------------------------------------------------------------------
echo NEO4J_HOME: $NEO4J_HOME
echo NEO4J_CSV_DIR: $NEO4J_CSV_DIR
echo NEO4J_DATA_DIR: $NEO4J_DATA_DIR
echo NEO4J_CSV_POSTFIX: $NEO4J_CSV_POSTFIX
echo ===============================================================================

: ${NEO4J_HOME:?"Environment variable NEO4J_HOME is unset or empty"}
: ${NEO4J_CSV_DIR:?"Environment variable NEO4J_CSV_DIR is unset or empty"}
: ${NEO4J_DATA_DIR:?"Environment variable NEO4J_DATA_DIR is unset or empty"}
: ${NEO4J_CSV_POSTFIX:?"Environment variable NEO4J_CSV_POSTFIX is unset or empty"}

./stop-neo4j-database.sh
./delete-neo4j-database.sh
./convert-csvs.sh
./import-to-neo4j.sh
./configure-neo4j.sh
./restart-neo4j.sh

echo Waiting for the database to start
while ! nc -z localhost 7687; do
  echo -n .
  sleep 1
done
echo
./create-indices.sh
