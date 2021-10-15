#!/bin/bash

set -e
set -o pipefail

: ${GRAPHDB_CONTAINER_NAME:?"Environment variable GRAPHDB_CONTAINER_NAME is unset or empty"}

docker stop ${GRAPHDB_CONTAINER_NAME} || echo "No container ${GRAPHDB_CONTAINER_NAME} found"

