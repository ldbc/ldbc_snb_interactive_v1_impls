#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ../config

: ${GRAPHDB_CONTAINER_ROOT:?"Environment variable GRAPHDB_CONTAINER_ROOT is unset or empty"}
: ${GRAPHDB_HEAP_SIZE:?"Environment variable GRAPHDB_HEAP_SIZE is unset or empty"}
: ${GRAPHDB_VERSION:?"Environment variable GRAPHDB_VERSION is unset or empty"}
: ${GRAPHDB_IMPORTRDF_CONTAINER_NAME:?"Environment variable GRAPHDB_IMPORTRDF_CONTAINER_NAME is unset or empty"}
: ${GRAPHDB_IMPORT_TTL_DIR:?"Environment variable GRAPHDB_IMPORT_TTL_DIR is unset or empty"}
: ${GRAPHDB_REPOSITORY_CONFIG_FILE:?"Environment variable GRAPHDB_REPOSITORY_CONFIG_FILE is unset or empty"}
: ${GRAPHDB_REPOSITORY_RULESET_FILE:?"Environment variable GRAPHDB_REPOSITORY_RULESET_FILE is unset or empty"}

docker-compose -f docker-compose-importrdf.yml up
