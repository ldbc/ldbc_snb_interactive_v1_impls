#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

rm -rf factors/*
cp -r ${LDBC_SNB_DATAGEN_DIR}/out-sf${SF}/factors/csv/raw/composite-merged-fk/* factors/
