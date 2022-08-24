#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

: ${GRAPHDB_DATA_DIR:?"Environment variable GRAPHDB_DATA_DIR is unset or empty"}

sudo rm -rf ${GRAPHDB_DATA_DIR}
