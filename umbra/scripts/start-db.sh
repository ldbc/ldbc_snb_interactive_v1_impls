#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

docker exec \
    --detach \
    ${UMBRA_CONTAINER_NAME} \
    /umbra/bin/server \
    --address 0.0.0.0 \
    /scratch/ldbc.db

echo -n "Waiting for the database to start ."
until python3 scripts/test-db-connection.py 2>&1 /dev/vull; do
    echo -n " ."
    sleep 1
done
echo
echo "Database started"