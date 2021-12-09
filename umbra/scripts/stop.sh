#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

docker rm -f ${UMBRA_CONTAINER_NAME} || echo "No container ${UMBRA_CONTAINER_NAME} found"
