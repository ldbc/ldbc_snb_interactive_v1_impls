#!/bin/bash

echo "Starting preprocessing CSV files"

# replace headers
while read line; do
  IFS=' ' read -r -a array <<< $line
  filename=${array[0]}
  header=${array[1]}
  sed -i.bkp "1s/.*/$header/" "${NEO4J_DATA_DIR}/${filename}${POSTFIX}"
done < headers.txt

# replace labels with one starting with an uppercase letter
sed -i.bkp "s/|city$/|City/" "${NEO4J_DATA_DIR}/static/place${POSTFIX}"
sed -i.bkp "s/|country$/|Country/" "${NEO4J_DATA_DIR}/static/place${POSTFIX}"
sed -i.bkp "s/|continent$/|Continent/" "${NEO4J_DATA_DIR}/static/place${POSTFIX}"
sed -i.bkp "s/|company|/|Company|/" "${NEO4J_DATA_DIR}/static/organisation${POSTFIX}"
sed -i.bkp "s/|university|/|University|/" "${NEO4J_DATA_DIR}/static/organisation${POSTFIX}"

# remove .bkp files
rm ${NEO4J_DATA_DIR}/*/*.bkp

echo "Finished preprocessing CSV files"
