#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

./stop-graphdb.sh
./delete-graphdb-database.sh
./graphdb-importrdf.sh
./start-graphdb.sh