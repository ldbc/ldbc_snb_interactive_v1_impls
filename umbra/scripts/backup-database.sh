#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

scripts/stop.sh
sudo rm -rf scratch/backup/
sudo cp -r scratch/db/ scratch/backup/
scripts/start.sh
