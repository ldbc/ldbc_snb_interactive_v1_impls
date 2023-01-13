#!/usr/bin/env bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

# to follow the logs (a'la "tail -f") use: "logs.sh -f"
docker logs $@ ${UMBRA_CONTAINER_NAME}
