cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

export GRAPHDB_CONTAINER_ROOT=`pwd`/scratch
export GRAPHDB_DATA_DIR=${GRAPHDB_CONTAINER_ROOT}/data
export GRAPHDB_IMPORT_TTL_DIR=`pwd`/test-data/
export GRAPHDB_REPOSITORY_CONFIG_FILE=`pwd`/config/graphdb-repo-config.ttl
export GRAPHDB_HEAP_SIZE=8g
export GRAPHDB_VERSION=9.9.1
export GRAPHDB_CONTAINER_NAME=snb-interactive-graphdb
export GRAPHDB_PRELOAD_CONTAINER_NAME=preload-graphdb
export GRAPHDB_DIST_ZIP_FOLDER_PATH=
export GRAPHDB_TTL_POSTFIX=_0_0.ttl
export GRAPHDB_PORT=7200
