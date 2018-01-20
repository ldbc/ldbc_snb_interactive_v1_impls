create table post (
    ps_postid bigint not null,
    ps_imagefile varchar,
    ps_creationdate timestamp without time zone not null,
    ps_locationip varchar not null,
    ps_browserused varchar not null,
    ps_language varchar,
    ps_content text not null,
    ps_length int not null,
    ps_creatorid bigint,
    ps_p_creatorid bigint,
    ps_locationid bigint,
    ps_forumid bigint,
    ps_replyof bigint,
    ps_country int
);

create table forum (
   f_forumid bigint not null,
   f_title varchar not null,
   f_creationdate timestamp without time zone not null,
   f_moderatorid bigint
);

create table forum_person (
   fp_forumid bigint not null,
   fp_personid bigint not null,
   fp_creationdate timestamp without time zone not null
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
   o_placeid bigint
);

create table person (
   p_personid bigint not null,
   p_firstname varchar not null,
   p_lastname varchar not null,
   p_gender varchar not null,
   p_birthday timestamp without time zone not null,
   p_creationdate timestamp without time zone not null,
   p_locationip varchar not null,
   p_browserused varchar not null,
   p_placeid bigint
);
--TODO: Add p_country int

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
   k_creationdate timestamp without time zone not null
);

create table likes (
   l_personid bigint not null,
   l_postid bigint not null,
   l_creationdate  timestamp without time zone not null
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
   pl_containerplaceid bigint
);

create table post_tag (
   pst_postid bigint not null,
   pst_tagid bigint not null
);

create table tagclass (
   tc_tagclassid bigint not null,
   tc_name varchar not null,
   tc_url varchar not null,
   tc_subclassoftagclassid bigint not null
);

create table tag (
   t_tagid bigint not null,
   t_name varchar not null,
   t_url varchar not null
);

create table tag_tagclass (
   ttc_tagid bigint not null,
   ttc_tagclassid bigint not null
);
