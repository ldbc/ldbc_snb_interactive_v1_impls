#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

scripts/stop.sh
scripts/start.sh
scripts/create-db.sh
scripts/load.sh
