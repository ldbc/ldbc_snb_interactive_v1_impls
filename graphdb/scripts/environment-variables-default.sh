cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

export GRAPHDB_CONTAINER_ROOT=`pwd`/scratch
export GRAPHDB_DATA_DIR=${GRAPHDB_CONTAINER_ROOT}/data
export GRAPHDB_TTL_DIR=`pwd`/test-data/
export REPOSITORY_CONFIG_FILE=`pwd`/config/graphdb-repo-config.ttl
export GRAPHDB_VERSION=9.9.1
export GRAPHDB_CONTAINER_NAME=snb-interactive-graphdb
export PRELOAD_CONTAINER_NAME=preload-graphdb
export GRAPHDB_PORT = 7200
