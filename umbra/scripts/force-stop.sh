#!/usr/bin/env bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

echo -n "Force stop and remove Umbra container . . ."
docker rm -f ${UMBRA_CONTAINER_NAME}
echo " Removed."
