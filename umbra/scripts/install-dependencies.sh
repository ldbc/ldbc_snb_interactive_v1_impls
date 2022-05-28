#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

if [[ ! -z $(which yum) ]]; then
    sudo yum install -y postgresql-devel
elif [[ ! -z $(which apt) ]]; then
    sudo apt update
    sudo apt install -y libpq5
else
    echo "Operating system not supported, please install the dependencies manually"
fi

pip3 install --user --progress-bar off psycopg
