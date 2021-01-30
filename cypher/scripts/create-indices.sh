#!/bin/bash

set -e

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

: ${NEO4J_CONTAINER_NAME:?"Environment variable NEO4J_CONTAINER_NAME is unset or empty"}

docker exec --interactive ${NEO4J_CONTAINER_NAME} cypher-shell < indices.cypher
