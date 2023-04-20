#!/usr/bin/env bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

if [ ! -d ${NEO4J_CSV_DIR} ]; then
    echo "Neo4j CSV directory does not exist. \${NEO4J_CSV_DIR} is set to: ${NEO4J_CSV_DIR}"
    exit 1
fi

echo "==============================================================================="
echo "Loading the Neo4j database"
echo "-------------------------------------------------------------------------------"
echo "NEO4J_CONTAINER_ROOT: ${NEO4J_CONTAINER_ROOT}"
echo "NEO4J_VERSION: ${NEO4J_VERSION}"
echo "NEO4J_CONTAINER_NAME: ${NEO4J_CONTAINER_NAME}"
echo "NEO4J_ENV_VARS: ${NEO4J_ENV_VARS}"
echo "NEO4J_DATA_DIR (on the host machine):"
echo "  ${NEO4J_DATA_DIR}"
echo "NEO4J_CSV_DIR (on the host machine):"
echo "  ${NEO4J_CSV_DIR}"
echo "==============================================================================="

scripts/stop.sh
scripts/delete-database.sh
scripts/import.sh
scripts/start.sh
scripts/create-indices.sh
