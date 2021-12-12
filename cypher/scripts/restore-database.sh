#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

scripts/stop-neo4j.sh
rm -rf scratch/data/
cp -r scratch/backup-data/ scratch/data/
cp -r scratch/backup-plugins/ scratch/plugins/
scripts/start-neo4j.sh
