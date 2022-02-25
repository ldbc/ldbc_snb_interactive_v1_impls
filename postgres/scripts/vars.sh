cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

export POSTGRES_PASSWORD=
export POSTGRES_DATABASE=ldbcsnb
export POSTGRES_USER=ldbcsnb
export POSTGRES_DATA_DIR=`pwd`/scratch/data
export POSTGRES_PORT=7484
