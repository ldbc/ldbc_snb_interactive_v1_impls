#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

if [ "$(uname)" == "Darwin" ]; then
  OS=osx
else
  OS=linux
fi

if [[ `uname -m` == "arm64" ]]; then
    ARCH=??
else
    ARCH=amd64
fi

cd scratch
wget -q https://github.com/duckdb/duckdb/releases/download/v${DUCKDB_VERSION}/duckdb_cli-${OS}-${ARCH}.zip -O duckdb_cli-${OS}-${ARCH}.zip
unzip -o duckdb_cli-${OS}-${ARCH}.zip
rm duckdb_cli-${OS}-${ARCH}.zip
