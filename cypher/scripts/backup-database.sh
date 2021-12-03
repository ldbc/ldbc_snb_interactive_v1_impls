#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

scripts/stop-neo4j.sh
sudo rm -rf scratch/backup/
sudo cp -r scratch/data/ scratch/backup/
scripts/start-neo4j.sh
