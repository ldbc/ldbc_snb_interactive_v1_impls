#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

docker exec -it ${HYPER_CONTAINER_NAME} psql --username=${HYPER_USER} --dbname=${HYPER_DATABASE}
