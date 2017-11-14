#!/bin/bash

# replace headers
while read line; do
  IFS=' ' read -r -a array <<< $line
  filename=${array[0]}
  header=${array[1]}
  sed -i "1s/.*/$header/" "${DATA_DIR}/${filename}${POSTFIX}"
done < headers.txt

# replace labels with one starting with an uppercase letter
sed -i "s/|city$/|City/" "${DATA_DIR}/place${POSTFIX}"
sed -i "s/|country$/|Country/" "${DATA_DIR}/place${POSTFIX}"
sed -i "s/|continent$/|Continent/" "${DATA_DIR}/place${POSTFIX}"
sed -i "s/|company|/|Company|/" "${DATA_DIR}/organisation${POSTFIX}"
sed -i "s/|university|/|University|/" "${DATA_DIR}/organisation${POSTFIX}"

# convert each date of format yyyy-mm-dd to a number of format yyyymmddd
sed -i "s#|\([0-9][0-9][0-9][0-9]\)-\([0-9][0-9]\)-\([0-9][0-9]\)|#|\1\2\3|#g" "${DATA_DIR}/person${POSTFIX}"

# convert each datetime of format yyyy-mm-ddThh:mm:ss.mmm+0000
# to a number of format yyyymmddhhmmssmmm
sed -i "s#|\([0-9][0-9][0-9][0-9]\)-\([0-9][0-9]\)-\([0-9][0-9]\)T\([0-9][0-9]\):\([0-9][0-9]\):\([0-9][0-9]\)\.\([0-9][0-9][0-9]\)+0000#|\1\2\3\4\5\6\7#g" ${DATA_DIR}/*${POSTFIX}
