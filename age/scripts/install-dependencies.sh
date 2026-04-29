#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

VENV=".venv"

if [[ ! -x "${VENV}/bin/python3" ]]; then
  python3 -m venv "${VENV}"
fi

"${VENV}/bin/pip" install -q psycopg2-binary
echo "Dependencies installed in ${VENV}."
