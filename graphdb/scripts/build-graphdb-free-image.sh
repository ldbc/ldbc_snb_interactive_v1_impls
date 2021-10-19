#!/bin/bash

set -eu
set -o pipefail

: ${GRAPHDB_VERSION:?"Environment variable GRAPHDB_VERSION is unset or empty"}
: ${GRAPHDB_DIST_ZIP_FOLDER_PATH:?"Environment variable GRAPHDB_DIST_ZIP_FOLDER_PATH is unset or empty"}

docker build -f config/Dockerfile --no-cache --pull --build-arg edition=free --build-arg version=${GRAPHDB_VERSION} -t ontotext/graphdb:${GRAPHDB_VERSION}-free ${GRAPHDB_DIST_ZIP_FOLDER_PATH}