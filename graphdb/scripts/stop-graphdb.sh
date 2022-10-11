#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

: ${GRAPHDB_CONTAINER_NAME:?"Environment variable GRAPHDB_CONTAINER_NAME is unset or empty"}

docker stop ${GRAPHDB_CONTAINER_NAME} || echo "No container ${GRAPHDB_CONTAINER_NAME} found"

