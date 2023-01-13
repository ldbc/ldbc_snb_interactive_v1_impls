#!/usr/bin/env bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

scripts/stop.sh
rm -rf scratch/{backup-data,backup-plugins}/
cp -r scratch/data/ scratch/backup-data/
cp -r scratch/plugins/ scratch/backup-plugins/
scripts/start.sh
