#!/bin/bash

set -e
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

docker run --rm \
    --publish=5432:5432 \
    --name ${POSTGRES_CONTAINER_NAME} \
    --env POSTGRES_PASSWORD=${POSTGRES_PASSWORD} \
    --volume=${POSTGRES_DATA_DIR}:/data \
    --detach \
    --shm-size=${POSTGRES_SHARED_MEMORY} \
    postgres:${POSTGRES_VERSION}

echo "Waiting for the database to start..."
sleep 10
echo "Database started"
