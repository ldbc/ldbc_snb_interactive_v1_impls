#!/bin/sh

DBNAME=$1
PATH=$2

/usr/bin/dropdb $DBNAME
/usr/bin/createdb $DBNAME --template template0 -l "C"
/usr/bin/psql -d $DBNAME -a -f schema.sql
(/bin/cat load.sql| /bin/sed "s|PATHVAR|$PATH|g"; echo "\q\n") | /usr/bin/psql -d $DBNAME
/usr/bin/psql -d $DBNAME -a -f schema_constraints.sql
