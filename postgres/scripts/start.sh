#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

scripts/stop.sh

echo "==============================================================================="
echo "Starting Postgres container with the following parameters"
echo "-------------------------------------------------------------------------------"
echo "POSTGRES_VERSION: ${POSTGRES_VERSION}"
echo "POSTGRES_CONTAINER_NAME: ${POSTGRES_CONTAINER_NAME}"
echo "POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}"
echo "POSTGRES_DATABASE: ${POSTGRES_DATABASE}"
echo "POSTGRES_SHARED_MEMORY: ${POSTGRES_SHARED_MEMORY}"
echo "POSTGRES_USER: ${POSTGRES_USER}"
echo "POSTGRES_PORT: ${POSTGRES_PORT}"
echo "POSTGRES_DATABASE_DIR (on the host machine):"
echo "  ${POSTGRES_DATABASE_DIR}"
echo "POSTGRES_CSV_DIR (on the host machine):"
echo "  ${POSTGRES_CSV_DIR}"
echo "==============================================================================="

if [ ! -d "${POSTGRES_CSV_DIR}" ]; then
    echo "Directory ${POSTGRES_CSV_DIR} does not exist."
    exit 1
fi

docker run --rm \
    --publish=${POSTGRES_PORT}:5432 \
    --name ${POSTGRES_CONTAINER_NAME} \
    --env POSTGRES_DATABASE=${POSTGRES_DATABASE} \
    --env POSTGRES_USER=${POSTGRES_USER} \
    --env POSTGRES_PASSWORD=${POSTGRES_PASSWORD} \
    --volume=${POSTGRES_CSV_DIR}:/data \
    --volume=${POSTGRES_DATABASE_DIR}:/var/lib/postgresql/data \
    --detach \
    --shm-size=${POSTGRES_SHARED_MEMORY} \
    postgres:${POSTGRES_VERSION} || exit 1

echo -n "Waiting for the database to start ."
until python3 scripts/test-db-connection.py > /dev/null 2>&1; do
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
