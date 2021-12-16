#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

: ${NEO4J_DATA_DIR:?"Environment variable NEO4J_DATA_DIR is unset or empty"}

rm -rf ${NEO4J_DATA_DIR}
