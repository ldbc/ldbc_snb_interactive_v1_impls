cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

export NEO4J_CONTAINER_NAME=snb-interactive-neo4j
export NEO4J_CONTAINER_ROOT=`pwd`/scratch
export NEO4J_DATA_DIR=${NEO4J_CONTAINER_ROOT}/data
export NEO4J_ENV_VARS=${NEO4J_ENV_VARS:-}
export NEO4J_HEADER_DIR=`pwd`/headers
export NEO4J_VERSION=${NEO4J_VERSION:-5.2.0}

if [[ `uname -m` == "arm64" ]]; then
    export NEO4J_DOCKER_PLATFORM_FLAG="--platform linux/arm64"
else
    export NEO4J_DOCKER_PLATFORM_FLAG=""
fi
