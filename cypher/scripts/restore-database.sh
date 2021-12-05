#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

scripts/stop-neo4j.sh
sudo rm -rf scratch/data/
sudo cp -r scratch/backup-data/ scratch/data/
sudo cp -r scratch/backup-plugins/ scratch/plugins/
sudo chown -R ${USER}:${USER} scratch/{data,plugins}/
scripts/start-neo4j.sh
