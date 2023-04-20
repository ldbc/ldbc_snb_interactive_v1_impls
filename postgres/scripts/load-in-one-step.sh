#!/usr/bin/env bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

if [ ! -d "${POSTGRES_CSV_DIR}" ]; then
    echo "PostgreSQL directory does not exist. \${POSTGRES_CSV_DIR} is set to: ${POSTGRES_CSV_DIR}"
    exit 1
fi

scripts/stop.sh
scripts/start.sh
scripts/create-db.sh
scripts/load.sh
