#!/bin/bash

cat indices.cypher | $NEO4J_HOME/bin/cypher-shell -u neo4j -p admin
