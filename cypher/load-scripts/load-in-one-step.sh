#!/bin/bash

./delete-neo4j-database.sh && \
  ./convert-csvs.sh && \
  ./import-to-neo4j.sh && \
  ./restart-neo4j.sh
