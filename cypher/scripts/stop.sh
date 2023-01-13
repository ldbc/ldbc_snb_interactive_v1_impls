#!/usr/bin/env bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

docker stop ${NEO4J_CONTAINER_NAME} || echo "No container ${NEO4J_CONTAINER_NAME} found"
