#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

pip3 install --user --progress-bar off psycopg
export DEBIAN_FRONTEND=noninteractive
apt -y install libpq5