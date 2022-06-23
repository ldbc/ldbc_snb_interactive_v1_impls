#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

cypher/scripts/install-dependencies.sh
postgres/scripts/install-dependencies.sh
umbra/scripts/install-dependencies.sh
