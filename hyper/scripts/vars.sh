cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

export HYPER_USER=ldbcuser
export HYPER_DATA_DIR=`pwd`/scratch/data
export HYPER_PORT=7484
