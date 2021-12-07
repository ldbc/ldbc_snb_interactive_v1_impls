#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

cp ddl/*.sql scratch/

time \
docker exec \
    --interactive \
    ${UMBRA_CONTAINER_NAME} \
    /umbra/bin/sql \
    --createdb /scratch/ldbc.db \
    /scratch/create-role.sql \
    /scratch/schema.sql \
    /scratch/snb-load.sql
    # \
    # /scratch/schema_constraints.sql \
    # /scratch/schema_foreign_keys.sql \
