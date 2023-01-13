#!/usr/bin/env bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

docker exec -i ${POSTGRES_CONTAINER_NAME} dropdb --if-exists ${POSTGRES_DATABASE} --username=${POSTGRES_USER}
docker exec -i ${POSTGRES_CONTAINER_NAME} createdb ${POSTGRES_DATABASE} --username=${POSTGRES_USER} --template template0 --locale "POSIX"
