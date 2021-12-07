cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

export POSTGRES_CONTAINER_NAME=snb-interactive-postgres
export POSTGRES_PASSWORD=mysecretpassword
export POSTGRES_DATABASE=ldbcsnb
export POSTGRES_USER=postgres
export POSTGRES_DATABASE_DIR=`pwd`/scratch/data
export POSTGRES_PORT=5432

export UMBRA_SCRATCH_DIR=`pwd`/scratch
export UMBRA_CONTAINER_NAME=snb-interactive-umbra
export UMBRA_DOCKER_IMAGE=umbra-fedora
