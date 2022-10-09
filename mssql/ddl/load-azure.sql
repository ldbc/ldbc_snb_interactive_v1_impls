USE ldbc;

CREATE MASTER KEY ENCRYPTION BY PASSWORD=':master_key_password';

CREATE DATABASE SCOPED CREDENTIAL LdbcStorageCredential
WITH IDENTITY = 'SHARED ACCESS SIGNATURE',
SECRET = ':azure_storage_sas_token';

CREATE EXTERNAL DATA SOURCE ldbcstorage
    WITH (
        TYPE = BLOB_STORAGE,
        LOCATION = ':azure_storage_endpoint',
        CREDENTIAL = LdbcStorageCredential
    );

-- Static --
-- Organisation
INSERT INTO [dbo].[Organisation] (id, type, name, url, LocationPlaceId)
SELECT    id,
    type,
    name,
    url,
    LocationPlaceId
FROM OPENROWSET (
    BULK ':organisation_csv',
    DATA_SOURCE = 'ldbcstorage',
    FORMATFILE = 'format-files/Organisation.xml',
    FORMATFILE_DATA_SOURCE = 'ldbcstorage',
    FIRSTROW = 2
) AS raw;

-- Place
INSERT INTO [dbo].[Place] (id, name, type, url, PartOfPlaceId)
SELECT    id,
    name,
    type,
    url,
    PartOfPlaceId
FROM OPENROWSET (
    BULK ':place_csv',
    DATA_SOURCE = 'ldbcstorage',
    FORMATFILE = 'format-files/Place.xml',
    FORMATFILE_DATA_SOURCE = 'ldbcstorage',
    FIRSTROW = 2
) AS raw;

-- Tag
INSERT INTO [dbo].[Tag] ($NODE_ID,id, name, url, TypeTagClassId)
SELECT  NODE_ID_FROM_PARTS(object_id('Tag'), id) AS node_id,  id,
    name,
    url,
    TypeTagClassId
FROM OPENROWSET (
    BULK ':tag_csv',
    DATA_SOURCE = 'ldbcstorage',
    FORMATFILE = 'format-files/Tag.xml',
    FORMATFILE_DATA_SOURCE = 'ldbcstorage',
    FIRSTROW = 2
) AS raw;

-- TagClass
INSERT INTO [dbo].[TagClass] (id, name, url, SubclassOfTagClassId)
SELECT  id,
    name,
    url,
    SubclassOfTagClassId
FROM OPENROWSET (
    BULK ':tagclass_csv',
    DATA_SOURCE = 'ldbcstorage',
    FORMATFILE = 'format-files/TagClass.xml',
    FORMATFILE_DATA_SOURCE = 'ldbcstorage',
    FIRSTROW = 2
) AS raw;

--Dynamic


-- Post
INSERT INTO [dbo].[Message] (
    $NODE_ID,
    creationDate,
    MessageId,
    content,
    imageFile,
    locationIP,
    browserUsed,
    length,
    language,
    CreatorPersonId,
    LocationCountryId,
    ContainerForumId,
    ParentMessageId
)
SELECT NODE_ID_FROM_PARTS(object_id('Message'), id) AS node_id,
    creationDate,
    id AS MessageId,
    content,
    imageFile,
    locationIP,
    browserUsed,
    length,
    language,
    CreatorPersonId,
    LocationCountryId,
    ContainerForumId,
    CAST(NULL AS bigint) AS ParentMessageId
FROM OPENROWSET (
    BULK ':post_csv',
    DATA_SOURCE = 'ldbcstorage',
    FORMATFILE = 'format-files/Post.xml',
    FORMATFILE_DATA_SOURCE = 'ldbcstorage',
    FIRSTROW = 2
) AS raw;

INSERT INTO [dbo].[Message] (
    $NODE_ID,
    creationDate,
    MessageId,
    content,
    imageFile,
    locationIP,
    browserUsed,
    length,
    language,
    CreatorPersonId,
    LocationCountryId,
    ContainerForumId,
    ParentMessageId
)
SELECT NODE_ID_FROM_PARTS(object_id('Message'), id) AS node_id,
    creationDate,
    id AS MessageId,
    content,
    CAST(NULL AS varchar(80)) AS imageFile,
    locationIP,
    browserUsed,
    length,
    CAST(NULL AS varchar(80)) AS language,
    CreatorPersonId,
    LocationCountryId,
    CAST(NULL AS bigint) AS ContainerForumId,
    coalesce(ParentPostId, ParentCommentId) AS ParentMessageId
FROM OPENROWSET (
    BULK ':comment_csv',
    DATA_SOURCE = 'ldbcstorage',
    FORMATFILE = 'format-files/Comment.xml',
    FORMATFILE_DATA_SOURCE = 'ldbcstorage',
    FIRSTROW = 2
) AS raw;


-- Comment_hasTag_Tag
INSERT INTO [dbo].[Message_hasTag_Tag] ($FROM_ID, $TO_ID,creationDate, MessageId, TagId)
SELECT NODE_ID_FROM_PARTS(object_id('Message'), MessageId) AS from_id,
       NODE_ID_FROM_PARTS(object_id('Tag'), TagId) AS to_id, 
        creationDate,
       MessageId,
       TagId
FROM OPENROWSET (
    BULK ':comment_hastag_tag_csv',
    DATA_SOURCE = 'ldbcstorage',
    FORMATFILE = 'format-files/Comment_hasTag_Tag.xml',
    FORMATFILE_DATA_SOURCE = 'ldbcstorage',
    FIRSTROW = 2
) AS raw;

INSERT INTO [dbo].[Message_hasTag_Tag] ($FROM_ID, $TO_ID,creationDate, MessageId, TagId)
SELECT NODE_ID_FROM_PARTS(object_id('Message'), MessageId) AS from_id,
       NODE_ID_FROM_PARTS(object_id('Tag'), TagId) AS to_id, 
  creationDate,
    MessageId,
    TagId
FROM OPENROWSET (
    BULK ':post_hastag_tag_csv',
    DATA_SOURCE = 'ldbcstorage',
    FORMATFILE = 'format-files/Post_hasTag_Tag.xml',
    FORMATFILE_DATA_SOURCE = 'ldbcstorage',
    FIRSTROW = 2
) AS raw;

-- Forum
INSERT INTO [dbo].[Forum] (creationDate, id, title, ModeratorPersonId)
SELECT       creationDate,
       id,
       title,
       ModeratorPersonId
FROM OPENROWSET (
    BULK ':forum_csv',
    DATA_SOURCE = 'ldbcstorage',
    FORMATFILE = 'format-files/Forum.xml',
    FORMATFILE_DATA_SOURCE = 'ldbcstorage',
    FIRSTROW = 2
) AS raw;

-- Forum_hasMember_Person
INSERT INTO [dbo].[Forum_hasMember_Person] (creationDate, ForumId, PersonId)
SELECT        creationDate,
        ForumId,
        PersonId
FROM OPENROWSET (
    BULK ':forum_hasmember_person_csv',
    DATA_SOURCE = 'ldbcstorage',
    FORMATFILE = 'format-files/Forum_hasMember_Person.xml',
    FORMATFILE_DATA_SOURCE = 'ldbcstorage',
    FIRSTROW = 2
) AS raw;

-- Forum_hasTag_Tag
INSERT INTO [dbo].[Forum_hasTag_Tag] (creationDate, ForumId, TagId)
SELECT       creationDate,
       ForumId,
       TagId
FROM OPENROWSET (
    BULK ':forum_hastag_tag_csv',
    DATA_SOURCE = 'ldbcstorage',
    FORMATFILE = 'format-files/Forum_hasTag_Tag.xml',
    FORMATFILE_DATA_SOURCE = 'ldbcstorage',
    FIRSTROW = 2
) AS raw;

-- Person 
INSERT INTO [dbo].[Person] (
    $NODE_ID,
    creationDate,
    personId,
    firstName,
    lastName,
    gender,
    birthday,
    locationIP,
    browserUsed,
    LocationCityId,
    language,
    email
)
SELECT NODE_ID_FROM_PARTS(object_id('Person'), personId) AS node_id,
       creationDate,
       personId,
       firstName,
       lastName,
       gender,
       birthday,
       locationIP,
       browserUsed,
       LocationCityId,
       language,
       email
FROM OPENROWSET (
    BULK ':person_csv',
    DATA_SOURCE = 'ldbcstorage',
    FORMATFILE = 'format-files/Person.xml',
    FORMATFILE_DATA_SOURCE = 'ldbcstorage',
    FIRSTROW = 2
) AS raw;

-- Person_hasInterest_Tag
INSERT INTO [dbo].[Person_hasInterest_Tag] (creationDate, PersonId, TagId)
SELECT creationDate,
       PersonId,
       TagId
FROM OPENROWSET (
    BULK ':person_hasinterest_tag_csv',
    DATA_SOURCE = 'ldbcstorage',
    FORMATFILE = 'format-files/Person_hasInterest_Tag.xml',
    FORMATFILE_DATA_SOURCE = 'ldbcstorage',
    FIRSTROW = 2
) AS raw;

-- Person_knows_Person
INSERT INTO [dbo].[Person_knows_Person] ($FROM_ID, $TO_ID, Person1id, Person2id, creationDate)
SELECT NODE_ID_FROM_PARTS(object_id('Person'), Person1id) AS from_id,
       NODE_ID_FROM_PARTS(object_id('Person'), Person2id) AS to_id,
       Person1id,
       Person2id,
       creationDate
FROM OPENROWSET (
    BULK ':person_knows_person_csv',
    DATA_SOURCE = 'ldbcstorage',
    FORMATFILE =  'format-files/Person_knows_Person.xml',
    FORMATFILE_DATA_SOURCE = 'ldbcstorage',
    FIRSTROW = 2
) AS raw;

INSERT INTO [dbo].[Person_knows_Person] ($FROM_ID, $TO_ID, Person1id, Person2id, creationDate)
SELECT NODE_ID_FROM_PARTS(object_id('Person'), Person2id) AS from_id,
       NODE_ID_FROM_PARTS(object_id('Person'), Person1id) AS to_id,
       Person2id,
       Person1id,
       creationDate
FROM OPENROWSET (
    BULK ':person_knows_person_csv',
    DATA_SOURCE = 'ldbcstorage',
    FORMATFILE =  'format-files/Person_knows_Person.xml',
    FORMATFILE_DATA_SOURCE = 'ldbcstorage',
    FIRSTROW = 2
) AS raw;

-- Person_likes_Comment
INSERT INTO [dbo].[Person_likes_Message] ($FROM_ID, $TO_ID,creationDate, PersonId, MessageId)
SELECT NODE_ID_FROM_PARTS(object_id('Person'), PersonId) AS from_id,
       NODE_ID_FROM_PARTS(object_id('Message'), MessageId) AS to_id,
      creationDate,
       PersonId,
       MessageId
FROM OPENROWSET (
    BULK ':person_likes_comment_csv',
    DATA_SOURCE = 'ldbcstorage',
    FORMATFILE = 'format-files/Person_likes_Comment.xml',
    FORMATFILE_DATA_SOURCE = 'ldbcstorage',
    FIRSTROW = 2
) AS raw;

-- Person_likes_Post
INSERT INTO [dbo].[Person_likes_Message] ($FROM_ID, $TO_ID, creationDate, PersonId, MessageId)
SELECT NODE_ID_FROM_PARTS(object_id('Person'), PersonId) AS from_id,
       NODE_ID_FROM_PARTS(object_id('Message'), MessageId) AS to_id,
    creationDate,
       PersonId,
       MessageId
FROM OPENROWSET (
    BULK ':person_likes_post_csv',
    DATA_SOURCE = 'ldbcstorage',
    FORMATFILE = 'format-files/Person_likes_Post.xml',
    FORMATFILE_DATA_SOURCE = 'ldbcstorage',
    FIRSTROW = 2
) AS raw;

-- Person_studyAt_University
INSERT INTO [dbo].[Person_studyAt_University] (creationDate, PersonId, UniversityId, classYear)
SELECT       creationDate,
       PersonId,
       UniversityId,
       classYear
FROM OPENROWSET (
    BULK ':person_studyat_university_csv',
    DATA_SOURCE = 'ldbcstorage',
    FORMATFILE = 'format-files/Person_studyAt_University.xml',
    FORMATFILE_DATA_SOURCE = 'ldbcstorage',
    FIRSTROW = 2
) AS raw;

-- Person_workAt_Company
INSERT INTO [dbo].[Person_workAt_Company] (creationDate, PersonId, CompanyId, workFrom)
SELECT       creationDate,
       PersonId,
       CompanyId,
       workFrom
FROM OPENROWSET (
    BULK ':person_workat_company_csv',
    DATA_SOURCE = 'ldbcstorage',
    FORMATFILE = 'format-files/Person_workAt_Company.xml',
    FORMATFILE_DATA_SOURCE = 'ldbcstorage',
    FIRSTROW = 2
) AS raw;

-- -- Load edge tables
INSERT INTO Message_hasCreator_Person($from_id, $to_id)
SELECT NODE_ID_FROM_PARTS(object_id('Message'), MessageId), NODE_ID_FROM_PARTS(object_id('Person'), CreatorPersonId)
FROM Message;

INSERT INTO [dbo].[Message_replyOf_Message] ($FROM_ID, $TO_ID)
SELECT NODE_ID_FROM_PARTS(object_id('Message'), id) AS from_id,
       NODE_ID_FROM_PARTS(object_id('Message'), coalesce(ParentPostId, ParentCommentId)) AS to_id
FROM OPENROWSET (
    BULK ':comment_csv',
    DATA_SOURCE = 'ldbcstorage',
    FORMATFILE = 'format-files/Comment.xml',
    FORMATFILE_DATA_SOURCE = 'ldbcstorage',
    FIRSTROW = 2
) AS raw;
