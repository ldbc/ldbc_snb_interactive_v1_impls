drop table comment_f;
drop table comment_hastag_tag_f;
drop table forum_f;
drop table forum_hasmember_person_f;
drop table forum_hastag_tag_f;
drop table organisation_f;
drop table person_f;
drop table person_email_emailaddress_f;
drop table person_hasinterest_tag_f;
drop table person_knows_person_f;
drop table person_likes_post_f;
drop table person_likes_comment_f;
drop table person_speaks_language_f;
drop table person_studyat_organisation_f;
drop table person_workat_organisation_f;
drop table place_f;
drop table post_f;
drop table post_hastag_tag_f;
drop table tagclass_f;
drop table tagclass_issubclassof_tagclass_f;
drop table tag_f;
drop table tag_hastype_tagclass_f;


-- create a table for representing comments.
create table comment_f (
    c_commentid bigint not null,
    c_creationdate datetime not null,
    c_locationip varchar not null,
    c_browserused varchar not null,
    c_content varchar not null,
    c_length int not null,
    c_creator bigint,
    c_place bigint,
    c_replyofpost bigint,
    c_replyofcomment bigint
);

create table comment_hastag_tag_f (
   ct_commentid bigint not null,
   ct_tagid bigint not null
);

create table forum_f (
   f_forumid bigint not null,
   f_title varchar not null,
   f_creationdate datetime not null,
   f_moderator bigint not null
);

create table forum_hasmember_person_f (
   fp_forumid bigint not null,
   fp_personid bigint not null,
   fp_creationdate datetime not null
);

create table forum_hastag_tag_f (
   ft_forumid bigint not null,
   ft_tagid bigint not null
);

create table organisation_f (
   o_organisationid bigint not null,
   o_type varchar not null,
   o_name varchar not null,
   o_url varchar not null,
   o_placeid bigint not null
);

create table person_f (
   p_personid bigint not null,
   p_firstname varchar not null,
   p_lastname varchar not null,
   p_gender varchar not null,
   p_birthday date not null,
   p_creationdate datetime not null,
   p_locationip varchar not null,
   p_browserused varchar not null,
   p_placeid bigint not null
);

create table person_email_emailaddress_f (
   pe_personid bigint not null,
   pe_email varchar not null
);

create table person_hasinterest_tag_f (
   pt_personid bigint not null,
   pt_tagid bigint not null
);

create table person_knows_person_f (
   pp_person1id bigint not null,
   pp_person2id bigint not null,
  pp_creationdate datetime
);

create table person_likes_post_f (
   pp_personid bigint not null,
   pp_postid bigint not null,
   pp_creationdate datetime not null
);

create table person_likes_comment_f (
   pp_personid bigint not null,
   pp_postid bigint not null,
   pp_creationdate datetime not null
);


create table person_speaks_language_f (
   pl_personid bigint not null,
   pl_language varchar not null
);

create table person_studyat_organisation_f (
   po_organisationid bigint not null,
   po_personid bigint not null,
   po_classyear int not null
);

create table person_workat_organisation_f (
   po_organisationid bigint not null,
   po_personid bigint not null,
   po_workfrom int not null
);

create table place_f (
   p_placeid bigint not null,
   p_name varchar not null,
   p_url varchar not null,
   p_type varchar not null,
   p_ispartof bigint
);

create table post_f (
    p_postid bigint not null,
    p_imagefile varchar,
    p_creationdate datetime not null,
    p_locationip varchar not null,
    p_browserused varchar not null,
    p_language varchar not null,
    p_content varchar not null,
    p_length int not null,
    p_creator bigint not null,
    p_forumid bigint not null,
    p_placeid bigint not null
);

create table post_hastag_tag_f (
   pt_postid bigint not null,
   pt_tagid bigint not null
);

create table tagclass_f (
   t_tagclassid bigint not null,
   t_name varchar not null,
   t_url varchar not null
);

create table tagclass_issubclassof_tagclass_f (
   tt_tagclass1id bigint not null,
   tt_tagclass2id bigint not null
);

create table tag_f (
   t_tagid bigint not null,
   t_name varchar not null,
   t_url varchar not null
);

create table tag_hastype_tagclass_f (
   tt_tagid bigint not null,
   tt_tagclassid bigint not null
);


