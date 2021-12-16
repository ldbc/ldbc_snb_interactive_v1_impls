#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

: ${NEO4J_CONTAINER_NAME:?"Environment variable NEO4J_CONTAINER_NAME is unset or empty"}

docker stop ${NEO4J_CONTAINER_NAME} || echo "No container ${NEO4J_CONTAINER_NAME} found"
