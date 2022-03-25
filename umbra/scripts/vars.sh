cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

export UMBRA_DDL_DIR=`pwd`/ddl/
export UMBRA_DATABASE_DIR=`pwd`/scratch/db/
export UMBRA_BACKUP_DIR=`pwd`/scratch/backup/
export UMBRA_CONTAINER_NAME=snb-interactive-umbra
export UMBRA_DOCKER_IMAGE=umbra-release:e7415d13f
