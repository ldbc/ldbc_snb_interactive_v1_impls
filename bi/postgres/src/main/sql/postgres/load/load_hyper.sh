#!/bin/sh

DBNAME=$1
PATHX=$2
PORT=12345

#TODO Set collate
#echo "drop database $DBNAME;" | /usr/bin/psql -h localhost -p $PORT
#echo "create database $DBNAME;" | /usr/bin/psql -h localhost -p $PORT
/usr/bin/psql -h localhost -p $PORT -d $DBNAME -a -f schema_hyper.sql
/bin/cat $PATHX/post_0_0.csv | awk -F '|' '{ print $1"|"$2"|"$3"|"$4"|"$5"|"$6"|"$7"|"$8"|"$9"|"$9"|"$11"|"$10"||"}' > $PATHX/post_0_0_converted.csv 
/bin/cat $PATHX/comment_0_0.csv | awk -F '|' '{print $1"||"$2"|"$3"|"$4"||"$5"|"$6"|"$7"||"$8"||"$9 $10"|"}' > $PATHX/comment_0_0_converted.csv
(/bin/cat load_hyper.sql| /bin/sed "s|PATHVAR|$PATHX|g"; echo "\q\n") | /usr/bin/psql -h localhost -p $PORT -d $DBNAME 
#/usr/bin/psql -h localhost -p $PORT -d $DBNAME -a -f schema_constraints.sql

