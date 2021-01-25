#!/bin/bash

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

export NEO4J_CONTAINER_ROOT=`pwd`/neo4j-scratch
export NEO4J_DATA_DIR=`pwd`/neo4j-scratch/data
export NEO4J_VERSION=4.2.1
export NEO4J_ENV_VARS="--env NEO4J_apoc_import_file_enabled=true"
export NEO4J_CSV_DIR=`pwd`/../../ldbc_snb_datagen/out/social_network
export NEO4J_CSV_POSTFIX=_0_0.csv
export NEO4J_CONTAINER_NAME="neo-snb"
