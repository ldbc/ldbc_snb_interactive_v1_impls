pushd .

cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

export POSTGRES_VERSION=13.3
export POSTGRES_CONTAINER_NAME=snb-interactive-postgres
export POSTGRES_PASSWORD=mysecretpassword
export POSTGRES_DATABASE=ldbcsnb
export POSTGRES_SHARED_MEMORY=8g
export POSTGRES_USER=postgres
export POSTGRES_DATABASE_DIR=`pwd`/scratch/data
export POSTGRES_PORT=5432

popd
