#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ../config

docker-compose -f docker-compose-preload.yml build
docker-compose -f docker-compose-preload.yml up -d

