#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

echo -n "Cleaning up running Umbra containers . . . "
docker rm -f ${UMBRA_CONTAINER_NAME} > /dev/null 2>&1 || true
echo "Cleanup completed."
