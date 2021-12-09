#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

rm -rf ${UMBRA_DATABASE_DIR}/*

echo -n "Loading database . . ."
docker exec \
    --interactive \
    ${UMBRA_CONTAINER_NAME} \
    /umbra/bin/sql \
    --createdb /scratch/db/ldbc.db \
    /ddl/create-role.sql \
    /ddl/schema.sql \
    /ddl/snb-load.sql \
    /ddl/schema_constraints.sql
echo " database loaded"
