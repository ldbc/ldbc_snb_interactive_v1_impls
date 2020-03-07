#!/bin/bash

# exit upon error
set -e

PG_DATA_DIR=${PG_DATA_DIR:-$(pwd)/../../../ldbc_snb_datagen/social_network/}
PG_DB_NAME=${PG_DB_NAME:-ldbcsf1}
PG_USER=${PG_USER:-$USER}
PG_FORCE_REGENERATE=${PG_FORCE_REGENERATE:-no}
PG_PORT=${PG_PORT:-5432}

# we regenerate PostgreSQL-specific CSV files for posts and comments, if either
#  - it doesn't exist
#  - the source CSV is newer
#  - we are forced to do so by environment variable PG_FORCE_REGENERATE=yes

if [ ! -f $PG_DATA_DIR/dynamic/post_0_0-postgres.csv -o $PG_DATA_DIR/dynamic/post_0_0.csv -nt $PG_DATA_DIR/dynamic/post_0_0-postgres.csv -o "${PG_FORCE_REGENERATE}x" = "yesx" ]; then
  cat $PG_DATA_DIR/dynamic/post_0_0.csv | \
    awk -F '|' '{ print $1"|"$2"|"$3"|"$4"|"$5"|"$6"|"$7"|"$8"|"$9"|"$10"|"$12"|"$11"|"}' > \
    $PG_DATA_DIR/dynamic/post_0_0-postgres.csv
fi
if [ ! -f $PG_DATA_DIR/dynamic/comment_0_0-postgres.csv -o $PG_DATA_DIR/dynamic/comment_0_0.csv -nt $PG_DATA_DIR/dynamic/comment_0_0-postgres.csv -o "${PG_FORCE_REGENERATE}x" = "yesx" ]; then
  cat $PG_DATA_DIR/dynamic/comment_0_0.csv | \
    awk -F '|' '{print $1"|"$2"|"$3"||"$4"|"$5"||"$6"|"$7"|"$8"|"$9"||"$10 $11}' > \
    $PG_DATA_DIR/dynamic/comment_0_0-postgres.csv
fi

/usr/bin/dropdb --if-exists $PG_DB_NAME -U $PG_USER -p $PG_PORT
/usr/bin/createdb $PG_DB_NAME -U $PG_USER -p $PG_PORT --template template0 -l "C"
/usr/bin/psql -d $PG_DB_NAME -U $PG_USER -p $PG_PORT -a -f schema.sql -v "ON_ERROR_STOP=1"
(cat snb-load.sql | sed "s|PATHVAR|$PG_DATA_DIR|g") | /usr/bin/psql -d $PG_DB_NAME -U $PG_USER -p $PG_PORT -v "ON_ERROR_STOP=1"
/usr/bin/psql -d $PG_DB_NAME -U $PG_USER -p $PG_PORT -a -f schema_constraints.sql -v "ON_ERROR_STOP=1"
