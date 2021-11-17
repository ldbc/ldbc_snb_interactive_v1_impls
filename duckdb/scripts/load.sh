#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

POSTGRES_FORCE_REGENERATE=${POSTGRES_FORCE_REGENERATE:-no}

# we regenerate PostgreSQL-specific CSV files for comments, if either
#  - it doesn't exist
#  - the source CSV is newer
#  - we are forced to do so by environment variable POSTGRES_FORCE_REGENERATE=yes

if [ ! -f ${POSTGRES_CSV_DIR}/dynamic/comment_0_0-postgres.csv -o ${POSTGRES_CSV_DIR}/dynamic/comment_0_0.csv -nt ${POSTGRES_CSV_DIR}/dynamic/comment_0_0-postgres.csv -o "${POSTGRES_FORCE_REGENERATE}x" = "yesx" ]; then
  cat ${POSTGRES_CSV_DIR}/dynamic/comment_0_0.csv | \
    awk -F '|' '{print $1"||"$2"|"$3"|"$4"||"$5"|"$6"|"$7"|"$8"||"$9 $10}' > \
    ${POSTGRES_CSV_DIR}/dynamic/comment_0_0-postgres.csv
fi

python3 load.py
