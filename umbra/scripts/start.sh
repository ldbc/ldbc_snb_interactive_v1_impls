#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

python3 -c 'import psycopg' || (echo "psycopg Python package is missing or broken" && exit 1)

scripts/stop.sh

if [ -n "${UMBRA_CSV_DIR-}" ]; then
    if [ ! -d "${UMBRA_CSV_DIR}" ]; then
        echo "Directory ${UMBRA_CSV_DIR} does not exist."
        exit 1
    fi
    export MOUNT_CSV_DIR="--volume=${UMBRA_CSV_DIR}:/data:z"
else
    export UMBRA_CSV_DIR="<unspecified>"
    export MOUNT_CSV_DIR=""
fi

# ensure that ${UMBRA_DATA_DIR} exists
mkdir -p "${UMBRA_DATA_DIR}"

echo "==============================================================================="
echo "Starting Umbra container"
echo "-------------------------------------------------------------------------------"
echo "UMBRA_VERSION: ${UMBRA_VERSION}"
echo "UMBRA_CONTAINER_NAME: ${UMBRA_CONTAINER_NAME}"
echo "UMBRA_PASSWORD: ${UMBRA_PASSWORD}"
echo "UMBRA_DATABASE: ${UMBRA_DATABASE}"
echo "UMBRA_USER: ${UMBRA_USER}"
echo "UMBRA_PORT: ${UMBRA_PORT}"
echo "UMBRA_DATA_DIR (on the host machine):"
echo "  ${UMBRA_DATA_DIR}"
echo "UMBRA_CSV_DIR (on the host machine):"
echo "  ${UMBRA_CSV_DIR}"
echo "==============================================================================="

docker run --rm \
    --user "$(id -u):$(id -g)" \
    --name ${UMBRA_CONTAINER_NAME} \
    --volume=${UMBRA_CSV_DIR}:/data/:z \
    --volume=${UMBRA_DATABASE_DIR}:/var/db/:z \
    --volume=${UMBRA_DDL_DIR}:/ddl/:z \
    --volume=${UMBRA_LOG_DIR}:/var/log/:z \
    --detach \
    --publish=8000:5432 \
    ${UMBRA_DOCKER_IMAGE} \
    umbra_server \
        --address 0.0.0.0 \
        /var/db/ldbc.db \
    >/dev/null

echo -n "Waiting for the database to start ."
until python3 scripts/test-db-connection.py 1>/dev/null 2>&1; do
    docker ps | grep ${UMBRA_CONTAINER_NAME} 1>/dev/null 2>&1 || (
        echo
        echo "Container lost."
        exit 1
    )
    echo -n " ."
    sleep 1
done
echo
echo "Database started"
