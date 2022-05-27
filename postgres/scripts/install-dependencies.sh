#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
sudo apt update && sudo apt install -y libpq5
pip3 install --user --progress-bar off psycopg