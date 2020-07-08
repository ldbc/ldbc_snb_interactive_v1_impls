#!/bin/bash

echo "MATCH (p:Person) RETURN count(p) AS numPersons;" | $NEO4J_HOME/bin/cypher-shell -u neo4j -p admin
