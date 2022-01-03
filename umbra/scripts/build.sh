#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

cd umbra-container/
wget ${UMBRA_URL}
tar xf umbra*.tar.xz
rm umbra*.tar.xz

docker build . --tag ${UMBRA_DOCKER_IMAGE}
