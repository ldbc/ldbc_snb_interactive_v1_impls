CREATE TABLE post (
    ps_postid int,
    ps_imagefile varchar,
    ps_creationdate date,
    ps_locationip varchar,
    ps_browserused varchar,
    ps_language varchar,
    ps_content varchar,
    ps_length int,
    ps_creatorid int,
    ps_p_creatorid int,
    ps_locationid int,
    ps_forumid int,
    ps_replyof int,
    ps_country int
)
FROM FILE 'data/post_0_0.csv'
LINE DELIMITED CSV (delimiter := '|')
;

CREATE TABLE forum (
   f_forumid int,
   f_title varchar,
   f_creationdate date,
   f_moderatorid int
)
FROM FILE 'data/forum_0_0.csv'
LINE DELIMITED CSV (delimiter := '|')
;

CREATE TABLE forum_person (
   fp_forumid int,
   fp_personid int,
   fp_creationdate date
)
FROM FILE 'data/forum_hasMember_person_0_0.csv'
LINE DELIMITED CSV (delimiter := '|')
;

CREATE TABLE forum_tag (
   ft_forumid int,
   ft_tagid int
)
FROM FILE 'data/forum_hasTag_tag_0_0.csv'
LINE DELIMITED CSV (delimiter := '|')
;

CREATE TABLE organisation (
   o_organisationid int,
   o_type varchar,
   o_name varchar,
   o_url varchar,
   o_placeid int
)
FROM FILE 'data/organisation_0_0.csv'
LINE DELIMITED CSV (delimiter := '|')
;

CREATE TABLE person (
   p_personid int,
   p_firstname varchar,
   p_lastname varchar,
   p_gender varchar,
   p_birthday date,
   p_creationdate date,
   p_locationip varchar,
   p_browserused varchar,
   p_placeid int
)
FROM FILE 'data/person_0_0.csv'
LINE DELIMITED CSV (delimiter := '|')
;
--TODO: Add p_country int

CREATE TABLE person_email (
   pe_personid int,
   pe_email varchar
)
FROM FILE 'data/person_email_emailaddress_0_0.csv'
LINE DELIMITED CSV (delimiter := '|')
;


CREATE TABLE person_tag (
   pt_personid int,
   pt_tagid int
)
FROM FILE 'data/person_hasInterest_tag_0_0.csv'
LINE DELIMITED CSV (delimiter := '|')
;

CREATE TABLE knows (
   k_person1id int,
   k_person2id int,
   k_creationdate date
)
FROM FILE 'data/person_knows_person_0_0.csv'
LINE DELIMITED CSV (delimiter := '|')
;

CREATE TABLE likes (
   l_personid int,
   l_postid int,
   l_creationdate  date
)
FROM FILE 'data/person_likes_post_0_0.csv'
LINE DELIMITED CSV (delimiter := '|')
;

CREATE TABLE person_language (
   plang_personid int,
   plang_language varchar
)
FROM FILE 'data/person_speaks_language_0_0.csv'
LINE DELIMITED CSV (delimiter := '|')
;

CREATE TABLE person_university (
   pu_personid int,
   pu_organisationid int,
   pu_classyear int
)
FROM FILE 'data/person_studyAt_organisation_0_0.csv'
LINE DELIMITED CSV (delimiter := '|')
;

CREATE TABLE person_company (
   pc_personid int,
   pc_organisationid int,
   pc_workfrom int
)
FROM FILE 'data/person_workAt_organisation_0_0.csv'
LINE DELIMITED CSV (delimiter := '|')
;

CREATE TABLE place (
   pl_placeid int,
   pl_name varchar,
   pl_url varchar,
   pl_type varchar,
   pl_containerplaceid int
)
FROM FILE 'data/place_0_0.csv'
LINE DELIMITED CSV (delimiter := '|')
;

CREATE TABLE post_tag (
   pst_postid int,
   pst_tagid int
)
FROM FILE 'data/post_hasTag_tag_0_0.csv'
LINE DELIMITED CSV (delimiter := '|')
;

CREATE TABLE tagclass (
   tc_tagclassid int,
   tc_name varchar,
   tc_url varchar,
   tc_subclassoftagclassid int
)
FROM FILE 'data/tag_0_0.csv'
LINE DELIMITED CSV (delimiter := '|')
;

CREATE TABLE tag (
   t_tagid int,
   t_name varchar,
   t_url varchar,
   t_tagclassid int
)
FROM FILE 'data/tagclass_0_0.csv'
LINE DELIMITED CSV (delimiter := '|')
;
