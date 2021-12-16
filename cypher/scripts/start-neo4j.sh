#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

if [ ! -d ${NEO4J_DATA_DIR} ]; then
    echo "Neo4j data directory does not exist"
    exit 1
fi

docker run --rm \
    --user="$(id -u):$(id -g)" \
    --publish=7474:7474 \
    --publish=7687:7687 \
    --detach \
    ${NEO4J_ENV_VARS} \
    --volume=${NEO4J_DATA_DIR}:/data:z \
    --volume=${NEO4J_CONTAINER_ROOT}/logs:/logs:z \
    --volume=${NEO4J_CONTAINER_ROOT}/plugins:/plugins:z \
    --env NEO4JLABS_PLUGINS='["apoc", "graph-data-science"]' \
    --env NEO4J_AUTH=none \
    --name ${NEO4J_CONTAINER_NAME} \
    neo4j:${NEO4J_VERSION}

echo -n "Waiting for the database to start ."
until docker exec --interactive --tty ${NEO4J_CONTAINER_NAME} cypher-shell "RETURN 'Database has started successfully' AS message" > /dev/null 2>&1; do
    echo -n " ."
    sleep 1
done
