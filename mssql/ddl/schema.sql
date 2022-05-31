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
    m_ps_imagefile varchar(MAX),
    m_creationdate datetimeoffset,
    m_locationip varchar(MAX) not null,
    m_browserused varchar(MAX) not null,
    m_ps_language varchar(MAX),
    m_content nvarchar(MAX),
    m_length int not null,
    m_creatorid bigint not null,
    m_ps_forumid bigint,
    m_locationid bigint not null
);

create table comment (
    m_messageid bigint primary key,
    m_creationdate datetimeoffset,
    m_locationip varchar(MAX) not null,
    m_browserused varchar(MAX) not null,
    m_content nvarchar(MAX),
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
    m_ps_imagefile varchar(MAX),
    m_creationdate datetimeoffset,
    m_locationip varchar(MAX) not null,
    m_browserused varchar(MAX) not null,
    m_ps_language varchar(MAX),
    m_content nvarchar(MAX),
    m_length int not null,
    m_creatorid bigint not null,
    m_locationid bigint not null,
    m_ps_forumid bigint, -- null for comments
    m_c_replyof bigint -- null for posts
);

create table forum (
   f_forumid bigint primary key,
   f_title nvarchar(MAX) not null,
   f_creationdate varchar(MAX),
   f_moderatorid bigint not null
);

create table forum_person (
   fp_forumid bigint not null,
   fp_personid bigint not null,
   fp_joindate datetimeoffset
);

create table forum_tag (
   ft_forumid bigint not null,
   ft_tagid bigint not null
);

create table organisation (
   o_organisationid bigint primary key,
   o_type varchar(MAX) not null,
   o_name nvarchar(MAX) not null,
   o_url varchar(MAX) not null,
   o_placeid bigint not null
);

create table person_temp (
   p_personid bigint primary key,
   p_firstname nvarchar(MAX) not null,
   p_lastname nvarchar(MAX) not null,
   p_gender varchar(MAX) not null,
   p_birthday date not null,
   p_creationdate datetimeoffset,
   p_locationip varchar(MAX) not null,
   p_browserused varchar(MAX) not null,
   p_placeid bigint not null
);

create table person (
   p_personid bigint primary key,
   p_firstname nvarchar(MAX) not null,
   p_lastname nvarchar(MAX) not null,
   p_gender varchar(MAX) not null,
   p_birthday date not null,
   p_creationdate datetimeoffset,
   p_locationip varchar(MAX) not null,
   p_browserused varchar(MAX) not null,
   p_placeid bigint not null
) AS NODE;

create table person_email (
   pe_personid bigint not null,
   pe_email varchar(MAX) not null
);

create table person_tag (
   pt_personid bigint not null,
   pt_tagid bigint not null
);

create table knows_temp (
   k_person1id bigint not null,
   k_person2id bigint not null,
   k_creationdate datetimeoffset
);

create table knows (
   k_person1id bigint not null,
   k_person2id bigint not null,
   k_creationdate datetimeoffset
) AS EDGE;

create table likes (
   l_personid bigint not null,
   l_messageid bigint not null,
   l_creationdate datetimeoffset
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
   pl_name nvarchar(MAX) not null,
   pl_url nvarchar(MAX) not null,
   pl_type nvarchar(MAX) not null,
   pl_containerplaceid bigint -- null for continents
);

create table message_tag (
   mt_messageid bigint not null,
   mt_tagid bigint not null
);

create table tagclass (
   tc_tagclassid bigint primary key,
   tc_name nvarchar(MAX) not null,
   tc_url varchar(MAX) not null,
   tc_subclassoftagclassid bigint -- null for the root tagclass (Thing)
);

create table tag (
   t_tagid bigint primary key,
   t_name nvarchar(MAX) not null,
   t_url varchar(MAX) not null,
   t_tagclassid bigint not null
);
