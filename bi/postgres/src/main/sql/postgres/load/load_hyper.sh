#!/bin/sh

DBNAME=$1
PATHX=$2

/usr/bin/psql -h localhost -p 7483 -a -f schema.sql
/bin/cat $PATHX/post_0_0.csv | awk -F '|' '{ print $1"|"$2"|"$3"|"$4"|"$5"|"$6"|"$7"|"$8"|"$9"|"$9"|"$11"|"$10"||"}' > $PATHX/post_0_0_converted.csv 
/bin/cat $PATHX/comment_0_0.csv | awk -F '|' '{print $1"||"$2"|"$3"|"$4"||"$5"|"$6"|"$7"||"$8"||"$9 $10"|"}' > $PATHX/comment_0_0_converted.csv
(/bin/cat load_hyper.sql| /bin/sed "s|PATHVAR|$PATHX|g"; echo "\q\n") | /usr/bin/psql -h localhost -p 7483
/usr/bin/psql -h localhost -p 7483 -a -f schema_constraints.sql

