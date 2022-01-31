#!/bin/bash

PARAM1=$1
DATA_PATH=${PARAM1:="/data/social_network"}
PARAM2=$2
QUERY_PATH=${PARAM2:="/queries"}

echo "==============================================================================="
echo "Setting up the TigerGraph database"
echo "-------------------------------------------------------------------------------"
echo "DATA_PATH: ${DATA_PATH}"
echo "QUERY_PATH: ${QUERY_PATH}"
echo "==============================================================================="

gsql create_schema.gsql
gsql create_database.gsql

gsql --graph LDBC_SNB load_static_1.gsql
gsql --graph LDBC_SNB load_static_2.gsql
gsql --graph LDBC_SNB load_dynamic_1.gsql
gsql --graph LDBC_SNB load_dynamic_2.gsql

gsql --graph LDBC_SNB RUN LOADING JOB load_static_1 USING organisation=\"$DATA_PATH/static/organisation_0_0.csv\", place=\"$DATA_PATH/static/place_0_0.csv\", tagclass=\"$DATA_PATH/static/tagclass_0_0.csv\", tagclass_isSubclassOf_tagclass=\"$DATA_PATH/static/tagclass_isSubclassOf_tagclass_0_0.csv\", tag=\"$DATA_PATH/static/tag_0_0.csv\", tag_hasType_tagclass=\"$DATA_PATH/static/tag_hasType_tagclass_0_0.csv\"
gsql --graph LDBC_SNB RUN LOADING JOB load_static_2 USING organisation_isLocatedIn_place=\"$DATA_PATH/static/organisation_isLocatedIn_place_0_0.csv\", place_isPartOf_place=\"$DATA_PATH/static/place_isPartOf_place_0_0.csv\"
gsql --graph LDBC_SNB RUN LOADING JOB load_dynamic_1 USING comment=\"$DATA_PATH/dynamic/comment_0_0.csv\", comment_hasCreator_person=\"$DATA_PATH/dynamic/comment_hasCreator_person_0_0.csv\", comment_hasTag_tag=\"$DATA_PATH/dynamic/comment_hasTag_tag_0_0.csv\", comment_isLocatedIn_place=\"$DATA_PATH/dynamic/comment_isLocatedIn_place_0_0.csv\", comment_replyOf_comment=\"$DATA_PATH/dynamic/comment_replyOf_comment_0_0.csv\", comment_replyOf_post=\"$DATA_PATH/dynamic/comment_replyOf_post_0_0.csv\", forum=\"$DATA_PATH/dynamic/forum_0_0.csv\", forum_containerOf_post=\"$DATA_PATH/dynamic/forum_containerOf_post_0_0.csv\", forum_hasMember_person=\"$DATA_PATH/dynamic/forum_hasMember_person_0_0.csv\", forum_hasModerator_person=\"$DATA_PATH/dynamic/forum_hasModerator_person_0_0.csv\", forum_hasTag_tag=\"$DATA_PATH/dynamic/forum_hasTag_tag_0_0.csv\", person=\"$DATA_PATH/dynamic/person_0_0.csv\", person_hasInterest_tag=\"$DATA_PATH/dynamic/person_hasInterest_tag_0_0.csv\", person_isLocatedIn_place=\"$DATA_PATH/dynamic/person_isLocatedIn_place_0_0.csv\", person_knows_person=\"$DATA_PATH/dynamic/person_knows_person_0_0.csv\", person_likes_comment=\"$DATA_PATH/dynamic/person_likes_comment_0_0.csv\", person_likes_post=\"$DATA_PATH/dynamic/person_likes_post_0_0.csv\", person_studyAt_organisation=\"$DATA_PATH/dynamic/person_studyAt_organisation_0_0.csv\", person_workAt_organisation=\"$DATA_PATH/dynamic/person_workAt_organisation_0_0.csv\", post=\"$DATA_PATH/dynamic/post_0_0.csv\", post_hasCreator_person=\"$DATA_PATH/dynamic/post_hasCreator_person_0_0.csv\", post_hasTag_tag=\"$DATA_PATH/dynamic/post_hasTag_tag_0_0.csv\", post_isLocatedIn_place=\"$DATA_PATH/dynamic/post_isLocatedIn_place_0_0.csv\"
#gsql --graph LDBC_SNB RUN LOADING JOB load_dynamic_2 USING person_speaks_language=\"$DATA_PATH/dynamic/person_email_emailaddress_0_0.csv\", person_has_emailaddress=\"$DATA_PATH/dynamic/person_speaks_language_0_0.csv\"

gsql --graph LDBC_SNB PUT ExprFunctions FROM \"$QUERY_PATH/ExprFunctions.hpp\"

gsql --graph LDBC_SNB $QUERY_PATH/interactiveShort1.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveShort2.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveShort3.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveShort4.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveShort5.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveShort6.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveShort7.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveComplex1.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveComplex2.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveComplex3.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveComplex4.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveComplex5.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveComplex6.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveComplex7.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveComplex8.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveComplex9.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveComplex10.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveComplex11.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveComplex12.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveComplex13.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveComplex14.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveInsert1.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveInsert2.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveInsert3.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveInsert4.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveInsert5.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveInsert6.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveInsert7.gsql
gsql --graph LDBC_SNB $QUERY_PATH/interactiveInsert8.gsql

gsql --graph LDBC_SNB INSTALL QUERY interactiveShort1
gsql --graph LDBC_SNB INSTALL QUERY interactiveShort2
gsql --graph LDBC_SNB INSTALL QUERY interactiveShort3
gsql --graph LDBC_SNB INSTALL QUERY interactiveShort4
gsql --graph LDBC_SNB INSTALL QUERY interactiveShort5
gsql --graph LDBC_SNB INSTALL QUERY interactiveShort6
gsql --graph LDBC_SNB INSTALL QUERY interactiveShort7
gsql --graph LDBC_SNB INSTALL QUERY interactiveComplex1
gsql --graph LDBC_SNB INSTALL QUERY interactiveComplex2
gsql --graph LDBC_SNB INSTALL QUERY interactiveComplex3
gsql --graph LDBC_SNB INSTALL QUERY interactiveComplex4
gsql --graph LDBC_SNB INSTALL QUERY interactiveComplex5
gsql --graph LDBC_SNB INSTALL QUERY interactiveComplex6
gsql --graph LDBC_SNB INSTALL QUERY interactiveComplex7
gsql --graph LDBC_SNB INSTALL QUERY interactiveComplex8
gsql --graph LDBC_SNB INSTALL QUERY interactiveComplex9
gsql --graph LDBC_SNB INSTALL QUERY interactiveComplex10
gsql --graph LDBC_SNB INSTALL QUERY interactiveComplex11
gsql --graph LDBC_SNB INSTALL QUERY interactiveComplex12
gsql --graph LDBC_SNB INSTALL QUERY interactiveComplex13
gsql --graph LDBC_SNB INSTALL QUERY interactiveComplex14
gsql --graph LDBC_SNB INSTALL QUERY interactiveInsert1
gsql --graph LDBC_SNB INSTALL QUERY interactiveInsert2
gsql --graph LDBC_SNB INSTALL QUERY interactiveInsert3
gsql --graph LDBC_SNB INSTALL QUERY interactiveInsert4
gsql --graph LDBC_SNB INSTALL QUERY interactiveInsert5
gsql --graph LDBC_SNB INSTALL QUERY interactiveInsert6
gsql --graph LDBC_SNB INSTALL QUERY interactiveInsert7
gsql --graph LDBC_SNB INSTALL QUERY interactiveInsert8
