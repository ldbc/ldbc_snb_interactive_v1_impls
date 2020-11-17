#!/bin/bash

export NEO4J_HOME=`pwd`/neo4j-server
export NEO4J_DATA_DIR=$NEO4J_HOME/data
export NEO4J_CSV_POSTFIX=_0_0.csv
export NEO4J_CSV_DIR=`pwd`/../../ldbc_snb_datagen/out/social_network
