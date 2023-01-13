#!/usr/bin/env bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

echo "==============================================================================="
echo "Loading the Umbra database"
echo "-------------------------------------------------------------------------------"
echo "UMBRA_VERSION: ${UMBRA_VERSION}"
echo "UMBRA_CONTAINER_NAME: ${UMBRA_CONTAINER_NAME}"
echo "UMBRA_DOCKER_IMAGE: ${UMBRA_DOCKER_IMAGE}"
echo "UMBRA_PASSWORD: ${UMBRA_PASSWORD}"
echo "UMBRA_USER: ${UMBRA_USER}"
echo "UMBRA_PORT: ${UMBRA_PORT}"
echo "UMBRA_CSV_DIR: ${UMBRA_CSV_DIR}"
echo "UMBRA_DDL_DIR: ${UMBRA_DDL_DIR}"
echo "UMBRA_LOG_DIR: ${UMBRA_LOG_DIR}"
echo "UMBRA_DATABASE_DIR: ${UMBRA_DATABASE_DIR}"
echo "UMBRA_BACKUP_DIR: ${UMBRA_BACKUP_DIR}"
echo "==============================================================================="

if [ ! -d "${UMBRA_CSV_DIR}" ]; then
    echo "Umbra directory does not exist. \${UMBRA_CSV_DIR} is set to: ${UMBRA_CSV_DIR}"
    exit 1
fi

scripts/stop.sh

start_time=$(date +%s.%3N)

scripts/create-db.sh
scripts/start.sh
scripts/load.sh $@

end_time=$(date +%s.%3N)
elapsed=$(echo "scale=3; $end_time - $start_time" | bc)
echo -e "Loading process took ${elapsed} seconds"
