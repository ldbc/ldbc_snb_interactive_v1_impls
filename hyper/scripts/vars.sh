cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

export HYPER_PASSWORD=
export HYPER_DATABASE=ldbcsnb
export HYPER_USER=ldbcsnb
export HYPER_DATA_DIR=`pwd`/scratch/data
export HYPER_PORT=7484
