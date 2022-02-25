cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

export HYPER_VERSION=14.1
export HYPER_CONTAINER_NAME=snb-interactive-postgres
export HYPER_PASSWORD=mysecretpassword
export HYPER_DATABASE=ldbcsnb
export HYPER_USER=postgres
export HYPER_DATA_DIR=`pwd`/scratch/data
export HYPER_PORT=5432
