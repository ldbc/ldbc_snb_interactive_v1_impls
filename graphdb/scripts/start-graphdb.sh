#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

: ${GRAPHDB_CONTAINER_ROOT:?"Environment variable GRAPHDB_CONTAINER_ROOT is unset or empty"}
: ${GRAPHDB_HEAP_SIZE:?"Environment variable GRAPHDB_HEAP_SIZE is unset or empty"}
: ${GRAPHDB_VERSION:?"Environment variable GRAPHDB_VERSION is unset or empty"}
: ${GRAPHDB_CONTAINER_NAME:?"Environment variable GRAPHDB_CONTAINER_NAME is unset or empty"}

docker run --rm \
    --publish=${GRAPHDB_PORT}:7200 \
    ${GRAPHDB_ENV_VARS} \
    --volume=${GRAPHDB_CONTAINER_ROOT}:/opt/graphdb/home:z \
    ontotext/graphdb:${GRAPHDB_VERSION}
