#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

scripts/stop.sh
rm -rf scratch/backup/
cp -r scratch/data/ scratch/backup/
scripts/start.sh
