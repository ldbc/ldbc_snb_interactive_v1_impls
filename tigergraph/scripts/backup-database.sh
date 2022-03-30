#!/bin/bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

echo "==============================================================================="
echo "Backup the TIGERGRAPH database"
echo "-------------------------------------------------------------------------------"
echo "TIGERGRAPH_VERSION: ${TIGERGRAPH_VERSION}"
echo "TIGERGRAPH_CONTAINER_NAME: ${TIGERGRAPH_CONTAINER_NAME}"
echo "==============================================================================="

docker exec --user tigergraph --interactive --tty ${TIGERGRAPH_CONTAINER_NAME} bash -c \
  "export PATH=/home/tigergraph/tigergraph/app/cmd:\$PATH; \
  gadmin config set System.Backup.Local.Enable true; \
  gadmin config set System.Backup.Local.Path /home/tigergraph/backup; \
  gadmin config apply -y; 
  export GSQL_USERNAME=tigergraph; \
  export GSQL_PASSWORD=tigergraph; \
  gbar backup -t snb-backup"