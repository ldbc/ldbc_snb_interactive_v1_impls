#!/usr/bin/env bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

python3 -c 'import psycopg' || (echo "psycopg Python package is missing or broken" && exit 1)

scripts/stop.sh

if [ -n "${POSTGRES_CSV_DIR-}" ]; then
    if [ ! -d "${POSTGRES_CSV_DIR}" ]; then
        echo "Directory ${POSTGRES_CSV_DIR} does not exist."
        exit 1
    fi
    export MOUNT_CSV_DIR="--volume=${POSTGRES_CSV_DIR}:/data:z"
else
    export POSTGRES_CSV_DIR="<unspecified>"
    export MOUNT_CSV_DIR=""
fi

if [ -n "${POSTGRES_CUSTOM_CONFIGURATION-}" ]; then
    if [ ! -f "${POSTGRES_CUSTOM_CONFIGURATION}" ]; then
        echo "Configuration file ${POSTGRES_CUSTOM_CONFIGURATION} does not exist."
        exit 1
    fi
    export POSTGRES_CUSTOM_MOUNTS="--volume=${POSTGRES_CUSTOM_CONFIGURATION}:/etc/postgresql.conf:z"
    export POSTGRES_CUSTOM_ARGS="--config_file=/etc/postgresql.conf"
else
    export POSTGRES_CUSTOM_CONFIGURATION="<unspecified>"
    export POSTGRES_CUSTOM_MOUNTS=""
    export POSTGRES_CUSTOM_ARGS=""
fi

# ensure that ${POSTGRES_DATA_DIR} exists
mkdir -p "${POSTGRES_DATA_DIR}"

echo "==============================================================================="
echo "Starting Postgres container"
echo "-------------------------------------------------------------------------------"
echo "POSTGRES_VERSION: ${POSTGRES_VERSION}"
echo "POSTGRES_CONTAINER_NAME: ${POSTGRES_CONTAINER_NAME}"
echo "POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}"
echo "POSTGRES_DATABASE: ${POSTGRES_DATABASE}"
echo "POSTGRES_USER: ${POSTGRES_USER}"
echo "POSTGRES_PORT: ${POSTGRES_PORT}"
echo "POSTGRES_CUSTOM_CONFIGURATION: ${POSTGRES_CUSTOM_CONFIGURATION}"
echo "POSTGRES_DATA_DIR (on the host machine):"
echo "  ${POSTGRES_DATA_DIR}"
echo "POSTGRES_CSV_DIR (on the host machine):"
echo "  ${POSTGRES_CSV_DIR}"
echo "==============================================================================="
docker pull postgres:${POSTGRES_VERSION}

docker run --rm \
    --user "$(id -u):$(id -g)" \
    --publish=${POSTGRES_PORT}:5432 \
    ${POSTGRES_DOCKER_PLATFORM_FLAG} \
    --shm-size=4g \
    --name ${POSTGRES_CONTAINER_NAME} \
    --env POSTGRES_DATABASE=${POSTGRES_DATABASE} \
    --env POSTGRES_USER=${POSTGRES_USER} \
    --env POSTGRES_PASSWORD=${POSTGRES_PASSWORD} \
    ${MOUNT_CSV_DIR} \
    --volume=${POSTGRES_DATA_DIR}:/var/lib/postgresql/data:z \
    --volume=${POSTGRES_DDL_DIR}:/ddl/:z \
    ${POSTGRES_CUSTOM_MOUNTS} \
    --detach \
    postgres:${POSTGRES_VERSION} \
    ${POSTGRES_CUSTOM_ARGS}

echo -n "Waiting for the database to start ."
until python3 scripts/test-db-connection.py 1>/dev/null 2>&1; do
    docker ps | grep ${POSTGRES_CONTAINER_NAME} 1>/dev/null 2>&1 || (
        echo
        echo "Container lost."
        exit 1
    )
    echo -n " ."
    sleep 1
done
echo
echo "Database started"
