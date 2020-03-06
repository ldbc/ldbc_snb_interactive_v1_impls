#!/bin/bash

$NEO4J_HOME/bin/neo4j stop
sleep 1
rm -rf $NEO4J_DATA_DIR
