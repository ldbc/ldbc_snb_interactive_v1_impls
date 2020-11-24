#!/bin/bash

echo "Starting preprocessing CSV files"

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

# replace headers
while read line; do
  IFS=' ' read -r -a array <<< $line
  FILENAME=${array[0]}
  HEADER=${array[1]}

  echo ${FILENAME}: ${HEADER}
  # replace header (no point using sed to save space as it creates a temporary file as well)
  echo ${HEADER} | cat - <(tail -n +2 ${NEO4J_CSV_DIR}/${FILENAME}${NEO4J_CSV_POSTFIX}) > tmpfile.csv && mv tmpfile.csv ${NEO4J_CSV_DIR}/${FILENAME}${NEO4J_CSV_POSTFIX}
done < headers.txt

echo "Finished preprocessing CSV files"
