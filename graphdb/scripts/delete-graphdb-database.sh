#!/bin/bash

set -e
set -o pipefail

: ${GRAPHDB_DATA_DIR:?"Environment variable GRAPHDB_DATA_DIR is unset or empty"}

rm -rf ${GRAPHDB_DATA_DIR}
