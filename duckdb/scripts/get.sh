#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

cd scratch

if [ "$(uname)" == "Darwin" ]; then
    OS=osx
    ARCH=universal
else
    OS=linux
    if [[ `uname -m` == "arm64" ]]; then
        ARCH=aarch64
        echo ${OS} ${ARCH} is currenty not supported
        return 1
    else
        ARCH=amd64
    fi
fi

wget -q https://github.com/duckdb/duckdb/releases/download/v${DUCKDB_VERSION}/duckdb_cli-${OS}-${ARCH}.zip -O duckdb_cli-${OS}-${ARCH}.zip
unzip -o duckdb_cli-${OS}-${ARCH}.zip
rm duckdb_cli-${OS}-${ARCH}.zip
