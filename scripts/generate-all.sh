#!/bin/bash

## Generate data sets, update streams, and parameters

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

export LDBC_SNB_IMPLS_DIR=`pwd`

USE_DATAGEN_DOCKER=${USE_DATAGEN_DOCKER:-false}
# set DATAGEN_COMMAND
if ${USE_DATAGEN_DOCKER}; then
    echo "Using Datagen Docker image"
    DATAGEN_COMMAND="docker run --volume ${LDBC_SNB_DATAGEN_DIR}/out-sf${SF}:/out ldbc/datagen-standalone:latest --cores $(nproc) --parallelism $(nproc) --memory ${LDBC_SNB_DATAGEN_MAX_MEM} --"
else
    echo "Using non-containerized Datagen"
    cd ${LDBC_SNB_DATAGEN_DIR}
    export LDBC_SNB_DATAGEN_JAR=$(sbt -batch -error 'print assembly / assemblyOutputPath')
    DATAGEN_COMMAND="tools/run.py --cores $(nproc) --parallelism $(nproc) --memory ${LDBC_SNB_DATAGEN_MAX_MEM} -- --output-dir ${LDBC_SNB_DATAGEN_DIR}/out-sf${SF}"
fi

cd ${LDBC_SNB_IMPLS_DIR}

echo "==================== Cleanup existing directories ===================="
mkdir -p update-streams-sf${SF}/
mkdir -p parameters-sf${SF}/
rm -rf ${LDBC_SNB_IMPLS_DIR}/update-streams-sf${SF}/*
rm -rf ${LDBC_SNB_IMPLS_DIR}/parameters-sf${SF}/*

echo "==================== Generate data ===================="
cd ${LDBC_SNB_DATAGEN_DIR}
if ${USE_DATAGEN_DOCKER} && [ -d out-sf${SF} ]; then
    sudo chown -R $(id -u):$(id -g) out-sf${SF}
fi
rm -rf out-sf${SF}

echo "-------------------- Generate data for Cypher --------------------"
if ${USE_DATAGEN_DOCKER} && [ -d out-sf${SF} ]; then
    sudo chown -R $(id -u):$(id -g) out-sf${SF}
fi
rm -rf out-sf${SF}/graphs/parquet/raw
${DATAGEN_COMMAND} \
    --mode bi \
    --format csv \
    --scale-factor ${SF} \
    --explode-edges \
    --epoch-millis \
    --format-options header=false,quoteAll=true,compression=gzip

echo "-------------------- Generate data for Postgres --------------------"
if ${USE_DATAGEN_DOCKER} && [ -d out-sf${SF} ]; then
    sudo chown -R $(id -u):$(id -g) out-sf${SF}
fi
rm -rf out-sf${SF}/graphs/parquet/raw
${DATAGEN_COMMAND} \
    --mode bi \
    --format csv \
    --scale-factor ${SF}

echo "-------------------- Generate data for update streams and factors --------------------"
if ${USE_DATAGEN_DOCKER} && [ -d out-sf${SF} ]; then
    sudo chown -R $(id -u):$(id -g) out-sf${SF}
fi
rm -rf out-sf${SF}/graphs/parquet/raw
${DATAGEN_COMMAND} \
    --mode bi \
    --format parquet \
    --scale-factor ${SF} \
    --generate-factors

if ${USE_DATAGEN_DOCKER}; then
    sudo chown -R $(id -u):$(id -g) out-sf${SF}
fi

export LDBC_SNB_DATA_ROOT_DIRECTORY=${LDBC_SNB_DATAGEN_DIR}/out-sf${SF}/

echo "==================== Generate update streams ===================="
cd ${LDBC_SNB_DRIVER_DIR}
cd scripts

export DATA_INPUT_TYPE=parquet
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
