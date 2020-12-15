#!/bin/bash

$NEO4J_HOME/bin/neo4j restart

echo "Waiting for the database to start"
until $NEO4J_HOME/bin/cypher-shell -u neo4j -p admin 'RETURN 42 AS x'; do
    echo -n .
    sleep 1
done
echo
