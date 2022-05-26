drop table if exists country;
drop table if exists forum_person;
drop table if exists forum_tag;
drop table if exists person_email;
drop table if exists person_tag;
drop table if exists person_language;
drop table if exists person_university;
drop table if exists person_company;
drop table if exists message_tag;
drop table if exists organisation;
drop table if exists knows;
drop table if exists likes;
drop table if exists tag;
drop table if exists tagclass;
drop table if exists message;
drop table if exists forum;
drop table if exists person;
drop table if exists place;

create table post (
    m_messageid bigint not null,
    m_ps_imagefile varchar,
    m_creationdate timestamp with time zone not null,
    m_locationip varchar not null,
    m_browserused varchar not null,
    m_ps_language varchar,
    m_content text,
    m_length int not null,
    m_creatorid bigint not null,
    m_ps_forumid bigint,
    m_locationid bigint not null
);

create table comment (
    m_messageid bigint not null,
    m_creationdate timestamp with time zone not null,
    m_locationip varchar not null,
    m_browserused varchar not null,
    m_content text,
    m_length int not null,
    m_creatorid bigint not null,
    m_locationid bigint not null,
    m_replyof_post bigint,
    m_replyof_comment bigint
);

create table forum (
   f_forumid bigint not null,
   f_title varchar not null,
   f_creationdate timestamp with time zone not null,
   f_moderatorid bigint not null
);

create table forum_person (
   fp_forumid bigint not null,
   fp_personid bigint not null,
   fp_joindate timestamp with time zone not null
);

create table forum_tag (
   ft_forumid bigint not null,
   ft_tagid bigint not null
);

create table organisation (
   o_organisationid bigint not null,
   o_type varchar not null,
   o_name varchar not null,
   o_url varchar not null,
   o_placeid bigint not null
);

create table person (
   p_personid bigint not null,
   p_firstname varchar not null,
   p_lastname varchar not null,
   p_gender varchar not null,
   p_birthday date not null,
   p_creationdate timestamp with time zone not null,
   p_locationip varchar not null,
   p_browserused varchar not null,
   p_placeid bigint not null
);

create table person_email (
   pe_personid bigint not null,
   pe_email varchar not null
);


create table person_tag (
   pt_personid bigint not null,
   pt_tagid bigint not null
);

create table knows (
   k_person1id bigint not null,
   k_person2id bigint not null,
   k_creationdate timestamp with time zone not null
);

create table likes (
   l_personid bigint not null,
   l_messageid bigint not null,
   l_creationdate timestamp with time zone not null
);

create table person_language (
   plang_personid bigint not null,
   plang_language varchar not null
);

create table person_university (
   pu_personid bigint not null,
   pu_organisationid bigint not null,
   pu_classyear int not null
);

create table person_company (
   pc_personid bigint not null,
   pc_organisationid bigint not null,
   pc_workfrom int not null
);

create table place (
   pl_placeid bigint not null,
   pl_name varchar not null,
   pl_url varchar not null,
   pl_type varchar not null,
   pl_containerplaceid bigint -- null for continents
);

create table message_tag (
   mt_messageid bigint not null,
   mt_tagid bigint not null
);

create table tagclass (
   tc_tagclassid bigint not null,
   tc_name varchar not null,
   tc_url varchar not null,
   tc_subclassoftagclassid bigint -- null for the root tagclass (Thing)
);

create table tag (
   t_tagid bigint not null,
   t_name varchar not null,
   t_url varchar not null,
   t_tagclassid bigint not null
);
