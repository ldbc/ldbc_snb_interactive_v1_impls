#!/bin/bash

NEO4J_VERSION=4.1.4
NEO4J_GDS_VERSION=1.4.0

rm -rf neo4j-server
if [ ! -f neo4j.tar.gz ]; then
  wget https://neo4j.com/artifact.php?name=neo4j-community-$NEO4J_VERSION-unix.tar.gz -O neo4j.tar.gz
fi
if [ ! -f neo4j-gds.zip ]; then
  wget https://s3-eu-west-1.amazonaws.com/com.neo4j.graphalgorithms.dist/graph-data-science/neo4j-graph-data-science-${NEO4J_GDS_VERSION}-standalone.zip -O neo4j-gds.zip
fi

tar xf neo4j.tar.gz
mv neo4j-community-$NEO4J_VERSION neo4j-server

# add GDS plugin and enable it
unzip neo4j-gds.zip
mv neo4j-graph-data-science-${NEO4J_GDS_VERSION}-standalone.jar neo4j-server/plugins
echo 'dbms.security.procedures.unrestricted=gds.*' >> neo4j-server/conf/neo4j.conf
echo 'dbms.security.procedures.whitelist=gds.*' >> neo4j-server/conf/neo4j.conf
