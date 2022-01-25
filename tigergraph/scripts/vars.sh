cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

#export TIGERGRAPH_VERSION=3.4.0
export TIGERGRAPH_VERSION=latest
export TIGERGRAPH_CONTAINER_NAME=tigergraph
export TIGERGRAPH_DATABASE=LDBC_SNB
export TIGERGRAPH_DATA_DIR=`pwd`/scratch/data
export TIGERGRAPH_SCRIPTS_DIR=`pwd`/setup
export TIGERGRAPH_QUERIES_DIR=`pwd`/queries
export TIGERGRAPH_REST_PORT=9000
export TIGERGRAPH_SSH_PORT=14022
export TIGERGRAPH_WEB_PORT=14240
export TIGERGRAPH_ENV_VARS=""
export TIGERGRAPH_ENDPOINT=http://127.0.0.1:$TIGERGRAPH_REST_PORT