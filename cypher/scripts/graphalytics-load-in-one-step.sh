#!/bin/bash
#!/bin/bash

echo ===============================================================================
echo Loading the Neo4j database with the following parameters
echo -------------------------------------------------------------------------------
echo NEO4J_HOME: $NEO4J_HOME
echo NEO4J_CSV_DIR: $NEO4J_CSV_DIR
echo NEO4J_DATA_DIR: $NEO4J_DATA_DIR
echo NEO4J_CSV_POSTFIX: $NEO4J_CSV_POSTFIX
echo ===============================================================================

./stop-neo4j-database.sh && \
  ./delete-neo4j-database.sh && \
  ./convert-csvs.sh && \
  ./graphalytics-import-to-neo4j.sh
  ./restart-neo4j.sh
