use ldbc;

drop view if exists country;
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
    m_messageid bigint primary key,
    m_ps_imagefile varchar(255),
    m_creationdate Datetime,
    m_locationip varchar(255) not null,
    m_browserused varchar(255) not null,
    m_ps_language varchar(255),
    m_content text,
    m_length int not null,
    m_creatorid bigint not null,
    m_ps_forumid bigint,
    m_locationid bigint not null
);

create table comment (
    m_messageid bigint primary key,
    m_creationdate Datetime,
    m_locationip varchar(255) not null,
    m_browserused varchar(255) not null,
    m_content text,
    m_length int not null,
    m_creatorid bigint not null,
    m_locationid bigint not null,
    m_replyof_post bigint,
    m_replyof_comment bigint
);

create table message (
    /*
     * m_ps_ denotes field specific to posts
     * m_c_  denotes field specific to comments
     * other m_ fields are common to posts and messages
     * Note: to distinguish between "post" and "comment" records:
     *   - m_c_replyof IS NULL for all "post" records
     *   - m_c_replyof IS NOT NULL for all "comment" records
     */
    m_messageid bigint primary key,
    m_ps_imagefile varchar(255),
    m_creationdate Datetime,
    m_locationip varchar(255) not null,
    m_browserused varchar(255) not null,
    m_ps_language varchar(255),
    m_content text,
    m_length int not null,
    m_creatorid bigint not null,
    m_locationid bigint not null,
    m_ps_forumid bigint, -- null for comments
    m_c_replyof bigint -- null for posts
);

create table forum (
   f_forumid bigint primary key,
   f_title varchar(255) not null,
   f_creationdate Datetime,
   f_moderatorid bigint not null
);

create table forum_person (
   fp_forumid bigint not null,
   fp_personid bigint not null,
   fp_joindate Datetime
);

create table forum_tag (
   ft_forumid bigint not null,
   ft_tagid bigint not null
);

create table organisation (
   o_organisationid bigint primary key,
   o_type varchar(255) not null,
   o_name varchar(255) not null,
   o_url varchar(255) not null,
   o_placeid bigint not null
);

create table person (
   p_personid bigint primary key,
   p_firstname varchar(255) not null,
   p_lastname varchar(255) not null,
   p_gender varchar(255) not null,
   p_birthday date not null,
   p_creationdate datetime2,
   p_locationip varchar(255) not null,
   p_browserused varchar(255) not null,
   p_placeid bigint not null
) AS NODE;

create table person_email (
   pe_personid bigint not null,
   pe_email varchar(255) not null
);


create table person_tag (
   pt_personid bigint not null,
   pt_tagid bigint not null
);

create table knows (
   k_person1id bigint not null,
   k_person2id bigint not null,
   k_creationdate Datetime
) AS EDGE;

create table likes (
   l_personid bigint not null,
   l_messageid bigint not null,
   l_creationdate Datetime
);

create table person_language (
   plang_personid bigint not null,
   plang_language varchar(255) not null
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
   pl_placeid bigint primary key,
   pl_name varchar(255) not null,
   pl_url varchar(255) not null,
   pl_type varchar(255) not null,
   pl_containerplaceid bigint -- null for continents
);

create table message_tag (
   mt_messageid bigint not null,
   mt_tagid bigint not null
);

create table tagclass (
   tc_tagclassid bigint primary key,
   tc_name varchar(255) not null,
   tc_url varchar(255) not null,
   tc_subclassoftagclassid bigint -- null for the root tagclass (Thing)
);

create table tag (
   t_tagid bigint primary key,
   t_name varchar(255) not null,
   t_url varchar(255) not null,
   t_tagclassid bigint not null
);
