#!/bin/bash

docker stop ${NEO4J_CONTAINER_NAME} || echo "No container ${NEO4J_CONTAINER_NAME} found"
