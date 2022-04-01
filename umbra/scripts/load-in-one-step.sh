#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

echo "==============================================================================="
echo "Loading the Umbra database"
echo "-------------------------------------------------------------------------------"
echo "UMBRA_DDL_DIR: ${UMBRA_DDL_DIR}"
echo "UMBRA_DATABASE_DIR: ${UMBRA_DATABASE_DIR}"
echo "UMBRA_CONTAINER_NAME: ${UMBRA_CONTAINER_NAME}"
echo "UMBRA_DOCKER_IMAGE: ${UMBRA_DOCKER_IMAGE}"
echo "UMBRA_CSV_DIR: ${UMBRA_CSV_DIR}"
echo "==============================================================================="

if [ ! -d "${UMBRA_CSV_DIR}" ]; then
    echo "Directory ${UMBRA_CSV_DIR} does not exist."
    exit 1
fi

scripts/stop.sh
scripts/create-db.sh
scripts/start.sh
scripts/load.sh
