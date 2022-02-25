#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

python3 -c 'import psycopg2' || (echo "psycopg2 Python package is missing or broken" && exit 1)

scripts/stop.sh

if [ -n "${HYPER_CSV_DIR-}" ]; then
    if [ ! -d "${HYPER_CSV_DIR}" ]; then
        echo "Directory ${HYPER_CSV_DIR} does not exist."
        exit 1
    fi
    export MOUNT_CSV_DIR="--volume=${HYPER_CSV_DIR}:/data:z"
else
    export HYPER_CSV_DIR="<unspecified>"
    export MOUNT_CSV_DIR=""
fi

if [ -n "${HYPER_CUSTOM_CONFIGURATION-}" ]; then
    if [ ! -f "${HYPER_CUSTOM_CONFIGURATION}" ]; then
        echo "Configuration file ${HYPER_CUSTOM_CONFIGURATION} does not exist."
        exit 1
    fi
    export HYPER_CUSTOM_MOUNTS="--volume=${HYPER_CUSTOM_CONFIGURATION}:/etc/postgresql.conf:z"
    export HYPER_CUSTOM_ARGS="--config_file=/etc/postgresql.conf"
else
    export HYPER_CUSTOM_CONFIGURATION="<unspecified>"
    export HYPER_CUSTOM_MOUNTS=""
    export HYPER_CUSTOM_ARGS=""
fi

# ensure that ${HYPER_DATA_DIR} exists
mkdir -p "${HYPER_DATA_DIR}"

echo "==============================================================================="
echo "Starting Postgres container"
echo "-------------------------------------------------------------------------------"
echo "HYPER_VERSION: ${HYPER_VERSION}"
echo "HYPER_CONTAINER_NAME: ${HYPER_CONTAINER_NAME}"
echo "HYPER_PASSWORD: ${HYPER_PASSWORD}"
echo "HYPER_DATABASE: ${HYPER_DATABASE}"
echo "HYPER_USER: ${HYPER_USER}"
echo "HYPER_PORT: ${HYPER_PORT}"
echo "HYPER_CUSTOM_CONFIGURATION: ${HYPER_CUSTOM_CONFIGURATION}"
echo "HYPER_DATA_DIR (on the host machine):"
echo "  ${HYPER_DATA_DIR}"
echo "HYPER_CSV_DIR (on the host machine):"
echo "  ${HYPER_CSV_DIR}"
echo "==============================================================================="

docker run --rm \
    --user "$(id -u):$(id -g)" \
    --publish=${HYPER_PORT}:5432 \
    --name ${HYPER_CONTAINER_NAME} \
    --env HYPER_DATABASE=${HYPER_DATABASE} \
    --env HYPER_USER=${HYPER_USER} \
    --env HYPER_PASSWORD=${HYPER_PASSWORD} \
    ${MOUNT_CSV_DIR} \
    --volume=${HYPER_DATA_DIR}:/var/lib/postgresql/data:z \
    ${HYPER_CUSTOM_MOUNTS} \
    --detach \
    postgres:${HYPER_VERSION} \
    ${HYPER_CUSTOM_ARGS}

echo -n "Waiting for the database to start ."
until python3 scripts/test-db-connection.py > /dev/null 2>&1; do
    docker ps | grep ${HYPER_CONTAINER_NAME} 1>/dev/null 2>&1 || (
        echo
        echo "Container lost."
        exit 1
    )
    echo -n " ."
    sleep 1
done
echo
echo "Database started"
