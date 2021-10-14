#!/bin/bash

set -eu
set -o pipefail

docker build --no-cache --pull --build-arg edition=free --build-arg version=${GRAPHDB_VERSION} -t ontotext/graphdb:${GRAPHDB_VERSION}-free ${GRAPHDB_DIST_ZIP_FOLDER_PATH}