#!/bin/bash

set -eu
set -o pipefail

cd "$(cd "$(dirname "${BASH_SOURCE[0]}")" >/dev/null 2>&1 && pwd)"
cd ..

. scripts/vars.sh

echo "==============================================================================="
echo "Loading the TIGERGRAPH database"
echo "-------------------------------------------------------------------------------"
echo "TIGERGRAPH_CONTAINER_NAME: ${TIGERGRAPH_CONTAINER_NAME}"
echo "TIGERGRAPH_VERSION: ${TIGERGRAPH_VERSION}"
echo "TIGERGRAPH_SCRIPTS_DIR: ${TIGERGRAPH_SCRIPTS_DIR}"
echo "TIGERGRAPH_DATA_DIR (on the host machine): ${TIGERGRAPH_DATA_DIR}"
echo "TIGERGRAPH_QUERIES_DIR: ${TIGERGRAPH_QUERIES_DIR}"
echo "TIGERGRAPH_ENV_VARS: ${TIGERGRAPH_ENV_VARS}"
echo "==============================================================================="


if [ ! -d ${TIGERGRAPH_DATA_DIR} ]; then
  echo "TigerGraph data directory does not exist!"
  exit 1
fi

if [ ! -d ${TIGERGRAPH_QUERIES_DIR} ]; then
  echo "TigerGraph queries directory does not exist!"
  exit 1
fi

if [ ! -d ${TIGERGRAPH_SCRIPTS_DIR} ]; then
  echo "TigerGraph scripts directory does not exist!"
  exit 1
fi


docker run --rm \
  --ulimit nofile=1000000:1000000 \
  --publish=$TIGERGRAPH_REST_PORT:9000 \
  --publish=$TIGERGRAPH_SSH_PORT:22 \
  --publish=$TIGERGRAPH_WEB_PORT:14240 \
  --detach \
  ${TIGERGRAPH_ENV_VARS} \
  --volume=${TIGERGRAPH_DATA_DIR}:/data:z \
  --volume=${TIGERGRAPH_SCRIPTS_DIR}:/scripts:z \
  --volume=${TIGERGRAPH_QUERIES_DIR}:/queries:z \
  --name ${TIGERGRAPH_CONTAINER_NAME} \
  docker.tigergraph.com/tigergraph:${TIGERGRAPH_VERSION}

echo -e "Waiting for the container to start.\n"
echo
until docker exec --user tigergraph --interactive --tty ${TIGERGRAPH_CONTAINER_NAME} /home/tigergraph/tigergraph/app/cmd/gadmin version >/dev/null 2>&1; do
  echo -n " ."
  sleep 1
done

echo -n "Starting the services."
until docker exec --user tigergraph --interactive --tty ${TIGERGRAPH_CONTAINER_NAME} /home/tigergraph/tigergraph/app/cmd/gadmin start all >/dev/null 2>&1; do
  echo -n " ."
  sleep 1
done

echo "Trial license is used. The license support benchmarks of SF-30 and smaller ones."
# Note: for SF-100 and larger you need to provide a license here
# echo "Applying the services."
# docker exec --user tigergraph --interactive --tty ${TIGERGRAPH_CONTAINER_NAME} /home/tigergraph/tigergraph/app/cmd/gadmin license set [license_string]

echo
echo "All done."
