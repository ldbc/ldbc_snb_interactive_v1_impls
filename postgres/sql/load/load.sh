#!/bin/sh

PATH=${1:-$(pwd)/../data}
DBNAME=${2:-ldbcsf1}
PG_USER=${3:-$USER}

/usr/bin/dropdb $DBNAME -U $PG_USER
/usr/bin/createdb $DBNAME -U $PG_USER --template template0 -l "C"
/usr/bin/psql -d $DBNAME -U $PG_USER -a -f schema.sql
(/bin/cat load.sql| /bin/sed "s|PATHVAR|$PATH|g"; echo "\q\n") | /usr/bin/psql -d $DBNAME -U $PG_USER
/usr/bin/psql -d $DBNAME -U $PG_USER -a -f schema_constraints.sql
