#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

: ${NEO4J_CONTAINER_ROOT:?"Environment variable NEO4J_CONTAINER_ROOT is unset or empty"}
: ${NEO4J_DATA_DIR:?"Environment variable NEO4J_DATA_DIR is unset or empty"}
: ${NEO4J_VERSION:?"Environment variable NEO4J_VERSION is unset or empty"}
: ${NEO4J_CONTAINER_NAME:?"Environment variable NEO4J_CONTAINER_NAME is unset or empty"}

if [ ! -d "${NEO4J_DATA_DIR}" ]; then
    echo "Neo4j data directory does not exist"
    exit 1
fi

DUMP_FILE=$1
if [ -z "${DUMP_FILE}" ]; then
  echo "Usage: $0 filename"
  exit 1
fi

docker run --interactive --tty --rm \
    --user="$(id -u):$(id -g)" \
    --volume="${NEO4J_DATA_DIR}":/data \
    --volume="${NEO4J_CONTAINER_ROOT}/conf":/conf \
    --volume="${NEO4J_CONTAINER_ROOT}/backups":/backups \
    --name "${NEO4J_CONTAINER_NAME}" \
    "neo4j:${NEO4J_VERSION}" \
    neo4j-admin dump --database=neo4j --to="/backups/${DUMP_FILE}"
