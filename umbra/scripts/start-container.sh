#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

scripts/stop.sh

mkdir -p ${UMBRA_DATABASE_DIR}/

echo -n "Starting container . . . "
docker run \
    --rm \
    --publish=5432:5432 \
    --volume=${UMBRA_CSV_DIR}:/data/:z \
    --volume=${UMBRA_DATABASE_DIR}:/scratch/db/:z \
    --volume=${UMBRA_DDL_DIR}:/ddl/:z \
    --name ${UMBRA_CONTAINER_NAME} \
    --detach \
    ${UMBRA_DOCKER_IMAGE}:latest
echo "Container started."
