#!/bin/bash

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

docker exec --interactive ${NEO4J_CONTAINER_NAME} cypher-shell < indices.cypher
