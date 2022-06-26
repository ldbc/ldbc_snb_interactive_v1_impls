cd "$( cd "$( dirname "${BASH_SOURCE[0]:-${(%):-%x}}" )" >/dev/null 2>&1 && pwd )"
cd ..

export NEO4J_CSV_DIR=${LDBC_SNB_DATAGEN_DIR}/out-sf${SF}/graphs/csv/bi/composite-projected-fk/
