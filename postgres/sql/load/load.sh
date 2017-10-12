#!/bin/bash

DB_DIR=${1:-$(pwd)/../data}
DBNAME=${2:-ldbcsf1}
PG_USER=${3:-$USER}

/usr/bin/dropdb $DBNAME -U $PG_USER
/usr/bin/createdb $DBNAME -U $PG_USER --template template0 -l "C"
/usr/bin/psql -d $DBNAME -U $PG_USER -a -f schema.sql
(cat load.sql | sed "s|PATHVAR|$DB_DIR|g"; echo "\q\n") | /usr/bin/psql -d $DBNAME -U $PG_USER
/usr/bin/psql -d $DBNAME -U $PG_USER -a -f schema_constraints.sql
