#!/bin/bash

: ${NEO4J_CONTAINER_NAME:?"Environment variable NEO4J_CONTAINER_NAME is unset or empty"}

docker stop ${NEO4J_CONTAINER_NAME} || echo "No container ${NEO4J_CONTAINER_NAME} found"
