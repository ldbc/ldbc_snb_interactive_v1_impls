#!/usr/bin/env bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

scripts/stop.sh
docker run \
    --volume=${UMBRA_DATABASE_DIR}:/var/db/:z \
    --volume=${UMBRA_BACKUP_DIR}:/var/backup/:z \
    ${UMBRA_DOCKER_IMAGE} \
    bash -c "rm -rf /var/backup/* && cp -r /var/db/* /var/backup/"
scripts/start.sh
