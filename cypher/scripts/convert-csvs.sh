#!/bin/bash

set -eu
set -o pipefail

echo "Starting preprocessing CSV files"

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

: ${NEO4J_CSV_POSTFIX:?"Environment variable NEO4J_CSV_POSTFIX is unset or empty"}
: ${NEO4J_VANILLA_CSV_DIR:?"Environment variable NEO4J_VANILLA_CSV_DIR is unset or empty"}
: ${NEO4J_CONVERTED_CSV_DIR:?"Environment variable NEO4J_CONVERTED_CSV_DIR is unset or empty"}

# provide progressbar is available
if command -v pv > /dev/null 2>&1; then
  SNB_CAT=pv
else
  SNB_CAT=cat
fi

# create converted directory / cleanup if it exists
rm -rf ${NEO4J_CONVERTED_CSV_DIR}/*
mkdir -p ${NEO4J_CONVERTED_CSV_DIR}/{static,dynamic}/

# replace headers
while read line; do
  IFS=' ' read -r -a array <<< $line
  FILENAME=${array[0]}
  HEADER=${array[1]}

  echo ${FILENAME}: ${HEADER}
  # replace header (no point using sed to save space as it creates a temporary file as well)
  if [ ! -f ${NEO4J_VANILLA_CSV_DIR}/${FILENAME}${NEO4J_CSV_POSTFIX} ]; then
    echo "${NEO4J_VANILLA_CSV_DIR}/${FILENAME}${NEO4J_CSV_POSTFIX} does not exist"
    exit 1
  fi
  echo ${HEADER} | ${SNB_CAT} - <(tail -n +2 ${NEO4J_VANILLA_CSV_DIR}/${FILENAME}${NEO4J_CSV_POSTFIX}) > ${NEO4J_CONVERTED_CSV_DIR}/${FILENAME}${NEO4J_CSV_POSTFIX}
done < scripts/headers.txt

# replace labels with one starting with an uppercase letter
sed -i.bkp "s/|city$/|City/" "${NEO4J_CONVERTED_CSV_DIR}/static/place${NEO4J_CSV_POSTFIX}"
sed -i.bkp "s/|country$/|Country/" "${NEO4J_CONVERTED_CSV_DIR}/static/place${NEO4J_CSV_POSTFIX}"
sed -i.bkp "s/|continent$/|Continent/" "${NEO4J_CONVERTED_CSV_DIR}/static/place${NEO4J_CSV_POSTFIX}"
sed -i.bkp "s/|company|/|Company|/" "${NEO4J_CONVERTED_CSV_DIR}/static/organisation${NEO4J_CSV_POSTFIX}"
sed -i.bkp "s/|university|/|University|/" "${NEO4J_CONVERTED_CSV_DIR}/static/organisation${NEO4J_CSV_POSTFIX}"
rm ${NEO4J_CONVERTED_CSV_DIR}/*/*.bkp

echo "Finished preprocessing CSV files"
