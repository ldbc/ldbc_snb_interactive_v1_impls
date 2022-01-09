#!/bin/bash
gsql create_schema.gsql
gsql create_database.gsql

gsql --graph LDBC_SNB load_static_1.gsql
gsql --graph LDBC_SNB load_static_2.gsql
gsql --graph LDBC_SNB load_dynamic_1.gsql

gsql --graph LDBC_SNB 'RUN LOADING JOB load_static_1 USING organisation="ANY:social_network/static/organisation_0_0.csv", place="ANY:social_network/static/place_0_0.csv", tagclass="ANY:social_network/static/tagclass_0_0.csv", tagclass_isSubclassOf_tagclass="ANY:social_network/static/tagclass_isSubclassOf_tagclass_0_0.csv", tag="ANY:social_network/static/tag_0_0.csv", tag_hasType_tagclass="ANY:social_network/static/tag_hasType_tagclass_0_0.csv"'
gsql --graph LDBC_SNB 'RUN LOADING JOB load_static_2 USING organisation_isLocatedIn_place="ANY:social_network/static/organisation_isLocatedIn_place_0_0.csv", place_isPartOf_place="ANY:social_network/static/place_isPartOf_place_0_0.csv"'
gsql --graph LDBC_SNB 'RUN LOADING JOB load_dynamic_1 USING comment="ANY:social_network/dynamic/comment_0_0.csv", comment_hasCreator_person="ANY:social_network/dynamic/comment_hasCreator_person_0_0.csv", comment_hasTag_tag="ANY:social_network/dynamic/comment_hasTag_tag_0_0.csv", comment_isLocatedIn_place="ANY:social_network/dynamic/comment_isLocatedIn_place_0_0.csv", comment_replyOf_comment="ANY:social_network/dynamic/comment_replyOf_comment_0_0.csv", comment_replyOf_post="ANY:social_network/dynamic/comment_replyOf_post_0_0.csv", forum="ANY:social_network/dynamic/forum_0_0.csv", forum_containerOf_post="ANY:social_network/dynamic/forum_containerOf_post_0_0.csv", forum_hasMember_person="ANY:social_network/dynamic/forum_hasMember_person_0_0.csv", forum_hasModerator_person="ANY:social_network/dynamic/forum_hasModerator_person_0_0.csv", forum_hasTag_tag="ANY:social_network/dynamic/forum_hasTag_tag_0_0.csv", person="ANY:social_network/dynamic/person_0_0.csv", person_hasInterest_tag="ANY:social_network/dynamic/person_hasInterest_tag_0_0.csv", person_isLocatedIn_place="ANY:social_network/dynamic/person_isLocatedIn_place_0_0.csv", person_knows_person="ANY:social_network/dynamic/person_knows_person_0_0.csv", person_likes_comment="ANY:social_network/dynamic/person_likes_comment_0_0.csv", person_likes_post="ANY:social_network/dynamic/person_likes_post_0_0.csv", person_studyAt_organisation="ANY:social_network/dynamic/person_studyAt_organisation_0_0.csv", person_workAt_organisation="ANY:social_network/dynamic/person_workAt_organisation_0_0.csv", post="ANY:social_network/dynamic/post_0_0.csv", post_hasCreator_person="ANY:social_network/dynamic/post_hasCreator_person_0_0.csv", post_hasTag_tag="ANY:social_network/dynamic/post_hasTag_tag_0_0.csv", post_isLocatedIn_place="ANY:social_network/dynamic/post_isLocatedIn_place_0_0.csv"'


gsql --graph LDBC_SNB 'PUT ExprFunctions FROM "queries/ExprFunctions.hpp"'

gsql --graph LDBC_SNB queries/interactiveShort1.gsql
gsql --graph LDBC_SNB queries/interactiveShort2.gsql
gsql --graph LDBC_SNB queries/interactiveShort3.gsql
gsql --graph LDBC_SNB queries/interactiveShort4.gsql
gsql --graph LDBC_SNB queries/interactiveShort5.gsql
gsql --graph LDBC_SNB queries/interactiveShort6.gsql
gsql --graph LDBC_SNB queries/interactiveShort7.gsql
gsql --graph LDBC_SNB queries/interactiveComplex1.gsql
gsql --graph LDBC_SNB queries/interactiveComplex2.gsql
gsql --graph LDBC_SNB queries/interactiveComplex3.gsql
gsql --graph LDBC_SNB queries/interactiveComplex4.gsql
gsql --graph LDBC_SNB queries/interactiveComplex5.gsql
gsql --graph LDBC_SNB queries/interactiveComplex6.gsql
gsql --graph LDBC_SNB queries/interactiveComplex7.gsql
gsql --graph LDBC_SNB queries/interactiveComplex8.gsql
gsql --graph LDBC_SNB queries/interactiveComplex9.gsql
gsql --graph LDBC_SNB queries/interactiveComplex10.gsql
gsql --graph LDBC_SNB queries/interactiveComplex11.gsql
gsql --graph LDBC_SNB queries/interactiveComplex12.gsql
gsql --graph LDBC_SNB queries/interactiveComplex13.gsql
gsql --graph LDBC_SNB queries/interactiveComplex14.gsql
gsql --graph LDBC_SNB queries/interactiveInsert1.gsql

gsql --graph LDBC_SNB "INSTALL QUERY interactiveShort1"
gsql --graph LDBC_SNB "INSTALL QUERY interactiveShort2"
gsql --graph LDBC_SNB "INSTALL QUERY interactiveShort3"
gsql --graph LDBC_SNB "INSTALL QUERY interactiveShort4"
gsql --graph LDBC_SNB "INSTALL QUERY interactiveShort5"
gsql --graph LDBC_SNB "INSTALL QUERY interactiveShort6"
gsql --graph LDBC_SNB "INSTALL QUERY interactiveShort7"
gsql --graph LDBC_SNB "INSTALL QUERY interactiveComplex1"
gsql --graph LDBC_SNB "INSTALL QUERY interactiveComplex2"
gsql --graph LDBC_SNB "INSTALL QUERY interactiveComplex3"
gsql --graph LDBC_SNB "INSTALL QUERY interactiveComplex4"
gsql --graph LDBC_SNB "INSTALL QUERY interactiveComplex5"
gsql --graph LDBC_SNB "INSTALL QUERY interactiveComplex6"
gsql --graph LDBC_SNB "INSTALL QUERY interactiveComplex7"
gsql --graph LDBC_SNB "INSTALL QUERY interactiveComplex8"
gsql --graph LDBC_SNB "INSTALL QUERY interactiveComplex9"
gsql --graph LDBC_SNB "INSTALL QUERY interactiveComplex10"
gsql --graph LDBC_SNB "INSTALL QUERY interactiveComplex11"
gsql --graph LDBC_SNB "INSTALL QUERY interactiveComplex12"
gsql --graph LDBC_SNB "INSTALL QUERY interactiveComplex13"
gsql --graph LDBC_SNB "INSTALL QUERY interactiveComplex14"
gsql --graph LDBC_SNB "INSTALL QUERY interactiveInsert1"
