#!/bin/bash

sed -i "s/|city$/|City/" "${DATA_DIR}/place${POSTFIX}"
sed -i "s/|country$/|Country/" "${DATA_DIR}/place${POSTFIX}"
sed -i "s/|continent$/|Continent/" "${DATA_DIR}/place${POSTFIX}"
sed -i "s/|company|/|Company|/" "${DATA_DIR}/organisation${POSTFIX}"
sed -i "s/|university|/|University|/" "${DATA_DIR}/organisation${POSTFIX}"
