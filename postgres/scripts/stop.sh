#!/usr/bin/env bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

docker rm -f ${POSTGRES_CONTAINER_NAME} || echo "No container ${POSTGRES_CONTAINER_NAME} found"
