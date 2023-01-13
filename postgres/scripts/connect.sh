#!/usr/bin/env bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

docker exec -it ${POSTGRES_CONTAINER_NAME} psql --username=${POSTGRES_USER} --dbname=${POSTGRES_DATABASE}
