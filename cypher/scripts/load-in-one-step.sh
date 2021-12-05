#!/bin/bash

set -e
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

echo "==============================================================================="
echo "Loading the Neo4j database"
echo "-------------------------------------------------------------------------------"
echo "NEO4J_CONTAINER_ROOT: ${NEO4J_CONTAINER_ROOT}"
echo "NEO4J_DATA_DIR: ${NEO4J_DATA_DIR}"
echo "NEO4J_VANILLA_CSV_DIR: ${NEO4J_VANILLA_CSV_DIR}"
echo "NEO4J_CONVERTED_CSV_DIR: ${NEO4J_CONVERTED_CSV_DIR}"
echo "NEO4J_CSV_POSTFIX: ${NEO4J_CSV_POSTFIX}"
echo "NEO4J_VERSION: ${NEO4J_VERSION}"
echo "NEO4J_CONTAINER_NAME: ${NEO4J_CONTAINER_NAME}"
echo "NEO4J_ENV_VARS: ${NEO4J_ENV_VARS}"
echo "==============================================================================="

: ${NEO4J_CONTAINER_ROOT:?"Environment variable NEO4J_CONTAINER_ROOT is unset or empty"}
: ${NEO4J_DATA_DIR:?"Environment variable NEO4J_DATA_DIR is unset or empty"}
: ${NEO4J_VANILLA_CSV_DIR:?"Environment variable NEO4J_VANILLA_CSV_DIR is unset or empty"}
: ${NEO4J_CONVERTED_CSV_DIR:?"Environment variable NEO4J_CONVERTED_CSV_DIR is unset or empty"}
: ${NEO4J_CSV_POSTFIX:?"Environment variable NEO4J_CSV_POSTFIX is unset or empty"}
: ${NEO4J_VERSION:?"Environment variable NEO4J_VERSION is unset or empty"}
: ${NEO4J_CONTAINER_NAME:?"Environment variable NEO4J_CONTAINER_NAME is unset or empty"}
# ${NEO4J_ENV_VARS} can be empty, hence no check is required

scripts/convert-csvs.sh
scripts/stop-neo4j.sh
scripts/delete-neo4j-database.sh
scripts/import-to-neo4j.sh
scripts/start-neo4j.sh
scripts/create-indices.sh
