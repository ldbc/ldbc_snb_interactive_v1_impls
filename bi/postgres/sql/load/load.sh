#!/bin/sh

DBNAME=$1
PATH=$2
PG_USER=$3

/usr/bin/dropdb $DBNAME -U $PG_USER
/usr/bin/createdb $DBNAME -U $PG_USER --template template0 -l "C"
/usr/bin/psql -d $DBNAME -U $PG_USER -a -f schema.sql
(/bin/cat load.sql| /bin/sed "s|PATHVAR|$PATH|g"; echo "\q\n") | /usr/bin/psql -d $DBNAME -U $PG_USER
/usr/bin/psql -d $DBNAME -U $PG_USER -a -f schema_constraints.sql
