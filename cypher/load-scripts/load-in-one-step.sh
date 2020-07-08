#!/bin/bash

./delete-neo4j-database.sh && \
	./convert-csvs.sh && \
	./import-to-neo4j.sh && \
	./restart-neo4j.sh && \
	echo "Sleep for 10 seconds to let the database start." \
	sleep 10 && \
	./create-indices.sh && \
	echo "Initiated asynchronous index creation. Please wait for a few minutes to let the database build them."
