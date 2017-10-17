#!/bin/bash

PG_CSV_DIR=${1:-$(pwd)/../test-data}
DB_NAME=${2:-ldbcsf1}
PG_USER=${3:-$USER}

cat $PG_CSV_DIR/post_0_0.csv | \
  awk -F '|' '{ print $1"|"$2"|"$3"|"$4"|"$5"|"$6"|"$7"|"$8"|"$9"|"$9"|"$11"|"$10"||"}' > \
  $PG_CSV_DIR/post_0_0-postgres.csv
cat $PG_CSV_DIR/comment_0_0.csv | \
  awk -F '|' '{print $1"||"$2"|"$3"|"$4"||"$5"|"$6"|"$7"||"$8"||"$9 $10"|"}' > \
  $PG_CSV_DIR/comment_0_0-postgres.csv

/usr/bin/dropdb $DB_NAME -U $PG_USER
/usr/bin/createdb $DB_NAME -U $PG_USER --template template0 -l "C"
/usr/bin/psql -d $DB_NAME -U $PG_USER -a -f schema.sql
(cat snb-load.sql | sed "s|PATHVAR|$PG_CSV_DIR|g"; echo "\q\n") | /usr/bin/psql -d $DB_NAME -U $PG_USER
/usr/bin/psql -d $DB_NAME -U $PG_USER -a -f schema_constraints.sql
