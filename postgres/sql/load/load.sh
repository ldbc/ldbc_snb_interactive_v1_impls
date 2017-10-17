#!/bin/bash

DB_DIR=${1:-$(pwd)/../data}
DBNAME=${2:-ldbcsf1}
PG_USER=${3:-$USER}

cat $DB_DIR/post_0_0.csv | \
  awk -F '|' '{ print $1"|"$2"|"$3"|"$4"|"$5"|"$6"|"$7"|"$8"|"$9"|"$9"|"$11"|"$10"||"}' > \
  $DB_DIR/post_0_0-postgres.csv
cat $DB_DIR/comment_0_0.csv | \
  awk -F '|' '{print $1"||"$2"|"$3"|"$4"||"$5"|"$6"|"$7"||"$8"||"$9 $10"|"}' > \
  $DB_DIR/comment_0_0-postgres.csv

/usr/bin/dropdb $DBNAME -U $PG_USER
/usr/bin/createdb $DBNAME -U $PG_USER --template template0 -l "C"
/usr/bin/psql -d $DBNAME -U $PG_USER -a -f schema.sql
(cat snb-load.sql | sed "s|PATHVAR|$DB_DIR|g"; echo "\q\n") | /usr/bin/psql -d $DBNAME -U $PG_USER
/usr/bin/psql -d $DBNAME -U $PG_USER -a -f schema_constraints.sql
