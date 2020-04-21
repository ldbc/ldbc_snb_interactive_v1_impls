#!/bin/bash

echo "Starting preprocessing CSV files"

# replace headers
while read line; do
  IFS=' ' read -r -a array <<< $line
  FILENAME=${array[0]}
  HEADER=${array[1]}

  echo ${FILENAME}: ${HEADER}
  # replace header (no point using sed to save space as it creates a temporary file as well)
  echo ${HEADER} | cat - <(tail -n +2 ${NEO4J_CSV_DIR}/${FILENAME}${NEO4J_CSV_POSTFIX}) > tmpfile.csv && mv tmpfile.csv ${NEO4J_CSV_DIR}/${FILENAME}${NEO4J_CSV_POSTFIX}
done < headers.txt

# replace labels with one starting with an uppercase letter
sed -i.bkp "s/|city$/|City/" "${NEO4J_CSV_DIR}/static/place${NEO4J_CSV_POSTFIX}"
sed -i.bkp "s/|country$/|Country/" "${NEO4J_CSV_DIR}/static/place${NEO4J_CSV_POSTFIX}"
sed -i.bkp "s/|continent$/|Continent/" "${NEO4J_CSV_DIR}/static/place${NEO4J_CSV_POSTFIX}"
sed -i.bkp "s/|company|/|Company|/" "${NEO4J_CSV_DIR}/static/organisation${NEO4J_CSV_POSTFIX}"
sed -i.bkp "s/|university|/|University|/" "${NEO4J_CSV_DIR}/static/organisation${NEO4J_CSV_POSTFIX}"

# remove .bkp files
rm ${NEO4J_CSV_DIR}/*/*.bkp

echo "Finished preprocessing CSV files"
