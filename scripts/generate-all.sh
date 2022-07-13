#!/bin/bash

## Generate data sets, update streams, and parameters

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

export LDBC_SNB_IMPLS_DIR=`pwd`

echo "==================== Cleanup existing directories ===================="
mkdir -p update-streams-sf${SF}/
mkdir -p parameters-sf${SF}/
rm -rf ${LDBC_SNB_IMPLS_DIR}/update-streams-sf${SF}/*
rm -rf ${LDBC_SNB_IMPLS_DIR}/parameters-sf${SF}/*

echo "==================== Generate data ===================="
cd ${LDBC_SNB_DATAGEN_DIR}

LDBC_SNB_DATAGEN_JAR=$(sbt -batch -error 'print assembly / assemblyOutputPath') && export LDBC_SNB_DATAGEN_JAR
rm -rf out-sf${SF}

echo "-------------------- Generate data for Cypher --------------------"
rm -rf out-sf${SF}/graphs/parquet/raw
tools/run.py \
    --cores $(nproc) \
    --memory ${LDBC_SNB_DATAGEN_MAX_MEM} \
    -- \
    --format csv \
    --scale-factor ${SF} \
    --explode-edges \
    --mode bi \
    --output-dir out-sf${SF}/ \
    --epoch-millis \
    --format-options header=false,quoteAll=true,compression=gzip

echo "-------------------- Generate data for Postgres and Paramgen --------------------"
rm -rf out-sf${SF}/graphs/parquet/raw
tools/run.py \
    --cores $(nproc) \
    --memory ${LDBC_SNB_DATAGEN_MAX_MEM} \
    -- \
    --format csv \
    --scale-factor ${SF} \
    --mode bi \
    --output-dir out-sf${SF} \
    --generate-factors

export LDBC_SNB_DATA_ROOT_DIRECTORY=${LDBC_SNB_DATAGEN_DIR}/out-sf${SF}/

echo "==================== Generate update streams ===================="
cd ${LDBC_SNB_DRIVER_DIR}
cd scripts

./convert.sh
mv inserts/ ${LDBC_SNB_IMPLS_DIR}/update-streams-sf${SF}/
mv deletes/ ${LDBC_SNB_IMPLS_DIR}/update-streams-sf${SF}/

echo "==================== Generate parameters ===================="
cd ${LDBC_SNB_IMPLS_DIR}
cd paramgen
scripts/get-factors.sh
scripts/paramgen.sh
cd ..

mv parameters/*.parquet parameters-sf${SF}/
