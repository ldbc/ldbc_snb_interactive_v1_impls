#!/usr/bin/env bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

export PGPASSWORD=${UMBRA_PASSWORD}
psql -h localhost -U postgres -p 8000 -d ldbcsnb
