#!/bin/bash

set -e

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

echo ===============================================================================
echo Loading the Neo4j database with the following parameters
echo -------------------------------------------------------------------------------
echo NEO4J_HOME: ${NEO4J_HOME}
echo NEO4J_DATA_DIR: ${NEO4J_DATA_DIR}
echo NEO4J_CSV_DIR: ${NEO4J_CSV_DIR}
echo NEO4J_CSV_POSTFIX: ${NEO4J_CSV_POSTFIX}
echo NEO4J_VERSION: ${NEO4J_VERSION}
echo NEO4J_CONTAINER_NAME: ${NEO4J_CONTAINER_NAME}
echo NEO4J_ENV_VARS: ${NEO4J_ENV_VARS}
echo ===============================================================================

: ${NEO4J_HOME:?"Environment variable NEO4J_HOME is unset or empty"}
: ${NEO4J_DATA_DIR:?"Environment variable NEO4J_DATA_DIR is unset or empty"}
: ${NEO4J_CSV_DIR:?"Environment variable NEO4J_CSV_DIR is unset or empty"}
: ${NEO4J_CSV_POSTFIX:?"Environment variable NEO4J_CSV_POSTFIX is unset or empty"}
: ${NEO4J_VERSION:?"Environment variable NEO4J_VERSION is unset or empty"}
: ${NEO4J_CONTAINER_NAME:?"Environment variable NEO4J_CONTAINER_NAME is unset or empty"}
# env vars can be empty, hence no check is required

./stop-neo4j.sh
./delete-neo4j-database.sh
./convert-csvs.sh
./import-to-neo4j.sh
./start-neo4j.sh
./create-indices.sh
