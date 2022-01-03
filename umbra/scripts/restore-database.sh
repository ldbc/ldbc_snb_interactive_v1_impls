#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

scripts/stop.sh
sudo rm -rf scratch/db
sudo cp -r scratch/backup/ scratch/db/
sudo chown -R ${USER}:${USER} scratch/db/
scripts/start.sh
