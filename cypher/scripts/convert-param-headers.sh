#!/bin/bash

set -e
set -o pipefail

echo "Starting preprocessing parameter files"

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

# replace headers
while read line; do
  IFS=' ' read -r -a array <<< $line
  FILENAME=${array[0]}
  HEADER=${array[1]}

  echo ${FILENAME}: ${HEADER}
  echo ${HEADER} | cat - <(tail -n +2 parameters/${FILENAME}.csv) > parameters/${FILENAME}.txt
done < parameters/headers.txt

echo "Finished preprocessing parameter files"
