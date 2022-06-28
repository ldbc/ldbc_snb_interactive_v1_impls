USE ldbc;
-- Static --

-- Organisation
INSERT INTO [dbo].[Organisation] (id, type, name, url, LocationPlaceId)
SELECT id,
       type,
       name,
       url,
       LocationPlaceId
FROM OPENROWSET (
    BULK ':organisation_csv',
    FORMATFILE = '/data/format-files/Organisation.xml',
    FIRSTROW = 2
) AS raw;

-- Place
INSERT INTO [dbo].[Place] (id, name, type, url, PartOfPlaceId)
SELECT id,
       name,
       type,
       url,
       PartOfPlaceId
FROM OPENROWSET (
    BULK ':place_csv',
    FORMATFILE = '/data/format-files/Place.xml',
    FIRSTROW = 2
) AS raw;

-- Tag
INSERT INTO [dbo].[Tag] (id, name, url, TypeTagClassId)
SELECT id,
       name,
       url,
       TypeTagClassId
FROM OPENROWSET (
    BULK ':tag_csv',
    FORMATFILE = '/data/format-files/Tag.xml',
    FIRSTROW = 2
) AS raw;

-- TagClass
INSERT INTO [dbo].[TagClass] (id, name, url, SubclassOfTagClassId)
SELECT id,
       name,
       url,
       SubclassOfTagClassId
FROM OPENROWSET (
    BULK ':tagclass_csv',
    FORMATFILE = '/data/format-files/TagClass.xml',
    FIRSTROW = 2
) AS raw;

--Dynamic
-- Comments
INSERT INTO [dbo].[Comment] (
    creationDate,
    id,
    locationIP,
    browserUsed,
    content,
    length,
    CreatorPersonId,
    LocationCountryId,
    ParentPostId,
    ParentCommentId
)
SELECT creationDate,
    id,
    locationIP,
    browserUsed,
    content,
    length,
    CreatorPersonId,
    LocationCountryId,
    ParentPostId,
    ParentCommentId
FROM OPENROWSET (
    BULK ':comment_csv',
    FORMATFILE = '/data/format-files/Comment.xml',
    FIRSTROW = 2
) AS raw;

-- Comment_hasTag_Tag
INSERT INTO [dbo].[Comment_hasTag_Tag] (creationDate, CommentId, TagId)
SELECT creationDate,
       CommentId,
       TagId
FROM OPENROWSET (
    BULK ':comment_hastag_tag_csv',
    FORMATFILE = '/data/format-files/Comment_hasTag_Tag.xml',
    FIRSTROW = 2
) AS raw;

-- Forum
INSERT INTO [dbo].[Forum] (creationDate, id, title, ModeratorPersonId)
SELECT creationDate,
       id,
       title,
       ModeratorPersonId
FROM OPENROWSET (
    BULK ':forum_csv',
    FORMATFILE = '/data/format-files/Forum.xml',
    FIRSTROW = 2
) AS raw;

-- Forum_hasMember_Person
INSERT INTO [dbo].[Forum_hasMember_Person] (creationDate, ForumId, PersonId)
SELECT creationDate,
       ForumId,
       PersonId
FROM OPENROWSET (
    BULK ':forum_hasmember_person_csv',
    FORMATFILE = '/data/format-files/Forum_hasMember_Person.xml',
    FIRSTROW = 2
) AS raw;

-- Forum_hasTag_Tag
INSERT INTO [dbo].[Forum_hasTag_Tag] (creationDate, ForumId, TagId)
SELECT creationDate,
       ForumId,
       TagId
FROM OPENROWSET (
    BULK ':forum_hastag_tag_csv',
    FORMATFILE = '/data/format-files/Forum_hasTag_Tag.xml',
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
SELECT NODE_ID_FROM_PARTS(object_id('Person'), id) AS node_id,
       creationDate,
       id,
       firstName,
       lastName,
       gender,
       birthday,
       locationIP,
       browserUsed,
       LocationCityId,
       language,
       email
FROM   OPENROWSET (
    BULK ':person_csv',
    FORMATFILE = '/data/format-files/Person.xml',
    FIRSTROW = 2
) AS raw;

-- Person_hasInterest_Tag
INSERT INTO [dbo].[Person_hasInterest_Tag] (creationDate, PersonId, TagId)
SELECT creationDate,
       PersonId,
       TagId
FROM OPENROWSET (
    BULK ':person_hasinterest_tag_csv',
    FORMATFILE = '/data/format-files/Person_hasInterest_Tag.xml',
    FIRSTROW = 2
) AS raw;

-- Person_knows_Person
INSERT INTO [dbo].[Person_knows_Person] ($FROM_ID, $TO_ID,Person1id, Person2id, creationDate)
SELECT NODE_ID_FROM_PARTS(object_id('Person'), Person1id) AS from_id,
       NODE_ID_FROM_PARTS(object_id('Person'), Person2id) AS to_id,
       Person1id,
       Person2id,
       creationDate
FROM OPENROWSET (
    BULK ':person_knows_person_csv',
    FORMATFILE =  '/data/format-files/Person_knows_Person.xml',
    FIRSTROW = 2
) AS raw;

-- Person_likes_Comment
INSERT INTO [dbo].[Person_likes_Comment] (creationDate, PersonId, CommentId)
SELECT creationDate,
       PersonId,
       CommentId
FROM OPENROWSET (
    BULK ':person_likes_comment_csv',
    FORMATFILE = '/data/format-files/Person_likes_Comment.xml',
    FIRSTROW = 2
) AS raw;

-- Person_likes_Post
INSERT INTO [dbo].[Person_likes_Post] (creationDate, PersonId, PostId)
SELECT creationDate,
       PersonId,
       PostId
FROM OPENROWSET (
    BULK ':person_likes_post_csv',
    FORMATFILE = '/data/format-files/Person_likes_Post.xml',
    FIRSTROW = 2
) AS raw;

-- Person_studyAt_University
INSERT INTO [dbo].[Person_studyAt_University] (creationDate, PersonId, UniversityId, classYear)
SELECT creationDate,
       PersonId,
       UniversityId,
       classYear
FROM OPENROWSET (
    BULK ':person_studyat_university_csv',
    FORMATFILE = '/data/format-files/Person_studyAt_University.xml',
    FIRSTROW = 2
) AS raw;

-- Person_workAt_Company
INSERT INTO [dbo].[Person_workAt_Company] (creationDate, PersonId, CompanyId, workFrom)
SELECT creationDate,
       PersonId,
       CompanyId,
       workFrom
FROM OPENROWSET (
    BULK ':person_studyat_university_csv',
    FORMATFILE = '/data/format-files/Person_workAt_Company.xml',
    FIRSTROW = 2
) AS raw;

-- Post
INSERT INTO [dbo].[Post] (
    creationDate,
    id,
    imageFile,
    locationIP,
    browserUsed,
    language,
    content,
    length,
    CreatorPersonId,
    ContainerForumId,
    LocationCountryId
)
SELECT creationDate,
    id,
    imageFile,
    locationIP,
    browserUsed,
    language,
    content,
    length,
    CreatorPersonId,
    ContainerForumId,
    LocationCountryId
FROM OPENROWSET (
    BULK ':post_csv',
    FORMATFILE = '/data/format-files/Post.xml',
    FIRSTROW = 2
) AS raw;

-- Post_hasTag_Tag
INSERT INTO [dbo].[Post_hasTag_Tag] (creationDate, PostId, TagId)
SELECT creationDate,
       PostId,
       TagId
FROM OPENROWSET (
    BULK ':post_hastag_tag_csv',
    FORMATFILE = '/data/format-files/Post_hasTag_Tag.xml',
    FIRSTROW = 2
) AS raw;
