#!/usr/bin/env bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

python3 -c 'import psycopg' || (echo "psycopg Python package is missing or broken" && exit 1)

scripts/stop.sh

docker run \
    --name ${UMBRA_CONTAINER_NAME} \
    --detach \
    --volume=${UMBRA_CSV_DIR}:/data/:z \
    --volume=${UMBRA_DATABASE_DIR}:/var/db/:z \
    --volume=${UMBRA_DDL_DIR}:/ddl/:z \
    --volume=${UMBRA_LOG_DIR}:/var/log/:z \
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
