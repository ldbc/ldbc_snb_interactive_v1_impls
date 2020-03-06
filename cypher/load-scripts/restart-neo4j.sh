#!/bin/bash

$NEO4J_HOME/bin/neo4j restart

echo Neo4j log:
tail -n 20 $NEO4J_HOME/logs/neo4j.log
