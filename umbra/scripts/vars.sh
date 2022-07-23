cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

export UMBRA_VERSION=75f99d093
export UMBRA_USER=postgres
export UMBRA_PASSWORD=mysecretpassword
export UMBRA_DATABASE=ldbcsnb
export UMBRA_DATA_DIR=`pwd`/scratch/data
export UMBRA_DDL_DIR=`pwd`/ddl/
export UMBRA_PORT=5432
export UMBRA_DOCKER_IMAGE=umbra-release:${UMBRA_VERSION}
export UMBRA_CONTAINER_NAME=snb-interactive-umbra
