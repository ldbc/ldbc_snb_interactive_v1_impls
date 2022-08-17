#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ../../parameters

for PARAMETER_FILE in $(ls *.parquet | sort --version-sort); do
    echo ${PARAMETER_FILE}
    echo "SELECT * FROM '${PARAMETER_FILE}' LIMIT 1;" | duckdb
done
