#!/bin/bash

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

if [ ! -d ${NEO4J_DATA_DIR} ]; then
    echo "Neo4j data is not loaded"
    exit 1
fi

docker run --rm \
    --user="$(id -u):$(id -g)" \
    --publish=7474:7474 \
    --publish=7687:7687 \
    --detach \
    ${NEO4J_ENV_VARS} \
    --volume=${NEO4J_DATA_DIR}:/data \
    --volume=${NEO4J_HOME}/logs:/logs \
    --volume=${NEO4J_HOME}/import:/var/lib/neo4j/import \
    --volume=${NEO4J_HOME}/plugins:/plugins \
    --env NEO4JLABS_PLUGINS='["apoc", "graph-data-science"]' \
    --env NEO4J_AUTH=none \
    --name ${NEO4J_CONTAINER_NAME} \
    neo4j:${NEO4J_VERSION} \

echo "Waiting for the database to start..."
until docker exec --interactive --tty ${NEO4J_CONTAINER_NAME} cypher-shell "RETURN 'Database has started successfully' AS message"; do
    sleep 1
done
