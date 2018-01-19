#!/bin/bash

export NEO4J_HOME=`pwd`/neo4j-server
export DB_DIR=$NEO4_HOME/data/databases/graph.db
$NEO4J_HOME/bin/neo4j-admin set-initial-password admin
