#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

docker exec -i ${UMBRA_CONTAINER_NAME} dropdb --if-exists ${UMBRA_DATABASE} --username=${UMBRA_USER}
docker exec -i ${UMBRA_CONTAINER_NAME} createdb ${UMBRA_DATABASE} --username=${UMBRA_USER} --template template0 --locale "POSIX"
