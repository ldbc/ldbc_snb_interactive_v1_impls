-- static tables

CREATE TABLE Organisation (
    id bigint not null,
    type varchar(12) not null,
    name varchar(256) not null,
    url varchar(256) not null,
    isLocatedIn_Place bigint
);

CREATE TABLE Place (
    id bigint not null,
    name varchar(256) not null,
    url varchar(256) not null,
    type varchar(12) not null,
    isPartOf_Place bigint
);

CREATE TABLE Tag (
    id bigint not null,
    name varchar(256) not null,
    url varchar(256) not null,
    hasType_TagClass bigint not null
);

CREATE TABLE TagClass (
    id bigint not null,
    name varchar(256) not null,
    url varchar(256) not null,
    isSubclassOf_TagClass bigint
);

-- static tables / separate table per individual subtype

CREATE TABLE Company (
    id bigint not null,
    name varchar(256) not null,
    url varchar(256) not null,
    isLocatedIn_Country bigint
);

CREATE TABLE University (
    id bigint not null,
    name varchar(256) not null,
    url varchar(256) not null,
    isLocatedIn_City bigint
);

CREATE TABLE Continent (
    id bigint not null,
    name varchar(256) not null,
    url varchar(256) not null
);

CREATE TABLE Country (
    id bigint not null,
    name varchar(256) not null,
    url varchar(256) not null,
    isPartOf_Continent bigint
);

CREATE TABLE City (
    id bigint not null,
    name varchar(256) not null,
    url varchar(256) not null,
    isPartOf_Country bigint
);

-- dynamic tables

CREATE TABLE Comment (
    creationDate timestamp without time zone not null,
    id bigint not null,
    locationIP varchar(40) not null,
    browserUsed varchar(40) not null,
    content varchar(2000) not null,
    length int not null,
    hasCreator_Person bigint not null,
    isLocatedIn_Country bigint not null,
    replyOf_Post bigint,
    replyOf_Comment bigint
);

CREATE TABLE Forum (
    creationDate timestamp without time zone not null,
    id bigint not null,
    title varchar(256) not null,
    hasModerator_Person bigint not null
);
CREATE TABLE Post (
    creationDate timestamp without time zone not null,
    id bigint not null,
    imageFile varchar(40),
    locationIP varchar(40) not null,
    browserUsed varchar(40) not null,
    language varchar(40),
    content varchar(2000),
    length int not null,
    hasCreator_Person bigint not null,
    Forum_containerOf bigint not null,
    isLocatedIn_Country bigint not null
);

CREATE TABLE Person (
    creationDate timestamp without time zone not null,
    id bigint not null,
    firstName varchar(40) not null,
    lastName varchar(40) not null,
    gender varchar(40) not null,
    birthday date not null,
    locationIP varchar(40) not null,
    browserUsed varchar(40) not null,
    isLocatedIn_City bigint not null,
    speaks varchar(640) not null,
    email varchar(8192) not null
);

-- edges
CREATE TABLE Comment_hasTag_Tag        (creationDate timestamp without time zone not null, id        bigint not null, hasTag_Tag         bigint not null);
CREATE TABLE Post_hasTag_Tag           (creationDate timestamp without time zone not null, id        bigint not null, hasTag_Tag         bigint not null);
CREATE TABLE Forum_hasMember_Person    (creationDate timestamp without time zone not null, id        bigint not null, hasMember_Person   bigint not null);
CREATE TABLE Forum_hasTag_Tag          (creationDate timestamp without time zone not null, id        bigint not null, hasTag_Tag         bigint not null);
CREATE TABLE Person_hasInterest_Tag    (creationDate timestamp without time zone not null, id        bigint not null, hasInterest_Tag    bigint not null);
CREATE TABLE Person_likes_Comment      (creationDate timestamp without time zone not null, id        bigint not null, likes_Comment      bigint not null);
CREATE TABLE Person_likes_Post         (creationDate timestamp without time zone not null, id        bigint not null, likes_Post         bigint not null);
CREATE TABLE Person_studyAt_University (creationDate timestamp without time zone not null, id        bigint not null, studyAt_University bigint not null, classYear int not null);
CREATE TABLE Person_workAt_Company     (creationDate timestamp without time zone not null, id        bigint not null, workAt_Company     bigint not null, workFrom  int not null);
CREATE TABLE Person_knows_Person       (creationDate timestamp without time zone not null, Person1id bigint not null, Person2id          bigint not null);

-- create table message (
--     /*
--      * m_ps_ denotes field specific to posts
--      * m_c_  denotes field specific to comments
--      * other m_ fields are common to posts and messages
--      * Note: to distinguish between "post" and "comment" records:
--      *   - m_c_replyof IS NULL for all "post" records
--      *   - m_c_replyof IS NOT NULL for all "comment" records
--      */

--     m_creationdate timestamp without time zone not null,
--     m_messageid bigint not null,
--     m_ps_imagefile varchar,
--     m_locationip varchar not null,
--     m_browserused varchar not null,
--     m_ps_language varchar,
--     m_content text not null,
--     m_length int not null,
--     m_creatorid bigint,
--     m_locationid bigint,
--     m_ps_forumid bigint,
--     m_c_replyof bigint
-- );

-- create table forum (
--    f_creationdate timestamp without time zone not null,
--    f_forumid bigint not null,
--    f_title varchar not null,
--    f_moderatorid bigint
-- );

-- create table forum_person (
--    fp_creationdate timestamp without time zone not null,
--    fp_forumid bigint not null,
--    fp_personid bigint not null
-- );

-- create table forum_tag (
--    ft_creationdate timestamp without time zone not null,
--    ft_forumid bigint not null,
--    ft_tagid bigint not null
-- );

-- create table organisation (
--    o_organisationid bigint not null,
--    o_type varchar not null,
--    o_name varchar not null,
--    o_url varchar not null,
--    o_placeid bigint
-- );

-- create table person (
--    p_creationdate timestamp without time zone not null,
--    p_personid bigint not null,
--    p_firstname varchar not null,
--    p_lastname varchar not null,
--    p_gender varchar not null,
--    p_birthday date not null,
--    p_locationip varchar not null,
--    p_browserused varchar not null,
--    p_placeid bigint
-- );

-- create table person_email (
--    pe_creationdate timestamp without time zone not null,
--    pe_personid bigint not null,
--    pe_email varchar not null
-- );


-- create table person_tag (
--    pt_creationdate timestamp without time zone not null,
--    pt_personid bigint not null,
--    pt_tagid bigint not null
-- );

-- create table knows (
--    k_creationdate timestamp without time zone not null,
--    k_person1id bigint not null,
--    k_person2id bigint not null
-- );

-- create table likes (
--    l_creationdate timestamp without time zone not null,
--    l_personid bigint not null,
--    l_messageid bigint not null
-- );

-- create table person_language (
--    plang_creationdate timestamp without time zone not null,
--    plang_personid bigint not null,
--    plang_language varchar not null
-- );

-- create table person_university (
--    pu_creationdate timestamp without time zone not null,
--    pu_personid bigint not null,
--    pu_organisationid bigint not null,
--    pu_classyear int not null
-- );

-- create table person_company (
--    pc_creationdate timestamp without time zone not null,
--    pc_personid bigint not null,
--    pc_organisationid bigint not null,
--    pc_workfrom int not null
-- );

-- create table place (
--    pl_placeid bigint not null,
--    pl_name varchar not null,
--    pl_url varchar not null,
--    pl_type varchar not null,
--    pl_containerplaceid bigint
-- );

-- create table message_tag (
--    mt_creationdate timestamp without time zone not null,
--    mt_messageid bigint not null,
--    mt_tagid bigint not null
-- );

-- create table tagclass (
--    tc_tagclassid bigint not null,
--    tc_name varchar not null,
--    tc_url varchar not null,
--    tc_subclassoftagclassid bigint
-- );

-- create table tag (
--    t_tagid bigint not null,
--    t_name varchar not null,
--    t_url varchar not null,
--    t_tagclassid bigint not null
-- );

