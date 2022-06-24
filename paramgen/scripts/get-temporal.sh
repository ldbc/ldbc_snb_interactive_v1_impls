#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

rm -rf temporal/*
cp -r ${LDBC_SNB_DATAGEN_DIR}/out-sf${SF}/graphs/parquet/raw/composite-merged-fk/dynamic/{Person,Person_knows_Person,Person_studyAt_University,Person_workAt_Company} temporal/
