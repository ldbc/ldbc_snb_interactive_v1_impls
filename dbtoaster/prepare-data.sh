#!/bin/bash

set -e

DATA_DIR='data'
RAW_DIR='data/raw'

FILE_NAME_SUFFIX='_0_0.csv'
#SED_SKIP_HEADER=(-e '2,$ p')

# post and comment transformations, copied from postgres/load-scripts/load.sh
cat $RAW_DIR/post_0_0.csv | \
  awk -F '|' '{ print $1"|"$2"|"$3"|"$4"|"$5"|"$6"|"$7"|"$8"|"$9"|"$9"|"$11"|"$10"||"}' > \
  $DATA_DIR/post_0_0.csv
cat $RAW_DIR/comment_0_0.csv | \
  awk -F '|' '{print $1"||"$2"|"$3"|"$4"||"$5"|"$6"|"$7"||"$8"||"$9 $10"|"}' | \
  sed -n -e '2,$ p' >> \
  $DATA_DIR/post_0_0.csv

# files that needs no specific transformations are copied
for f in forum forum_hasMember_person forum_hasTag_tag \
         organisation \
         person person_email_emailaddress person_hasInterest_tag person_knows_person \
         person_likes_post person_speaks_language \
         person_studyAt_organisation person_workAt_organisation \
         post_hasTag_tag tag tagclass \
         place; do
  cp $RAW_DIR/$f$FILE_NAME_SUFFIX $DATA_DIR/$f$FILE_NAME_SUFFIX
done


for f in $DATA_DIR/*$FILE_NAME_SUFFIX ; do
  # skip header row
  sed -i -n -e '2,$ p' $f

  # replace empty (NULL) fields by -1
  perl -ne 's/\|(?=(\||$))/|-1/g; print;' $f >$f.new
  mv $f.new $f

  # remove time component from date fields
  sed -i -e 's/\(|\|^\)\([0-9]\{4\}-[0-9]\{2\}-[0-9]\{2\}\)T[0-9]\{2\}:[0-9]\{2\}:[0-9]\{2\}\.[0-9]\{3\}+[0-9]\{4\}\(|\|$\)/\1\2\3/g' $f
done