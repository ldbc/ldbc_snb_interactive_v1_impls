#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

NEO4J_PACKAGE_VERSION=$(sed -E 's/[^.0-9]//g' <<< ${NEO4J_VERSION})

pip3 install --user neo4j==${NEO4J_PACKAGE_VERSION} python-dateutil
