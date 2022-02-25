#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

docker exec -i ${HYPER_CONTAINER_NAME} dropdb --if-exists ${HYPER_DATABASE} --username=${HYPER_USER}
docker exec -i ${HYPER_CONTAINER_NAME} createdb ${HYPER_DATABASE} --username=${HYPER_USER} --template template0 --locale "POSIX"
