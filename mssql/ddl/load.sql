USE ldbc;
-- Static --
-- Organisation
INSERT INTO [dbo].[Organisation] ($NODE_ID, id, type, name, url, LocationPlaceId)
SELECT NODE_ID_FROM_PARTS(object_id('Organisation'), id) AS node_id,
    id,
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
INSERT INTO [dbo].[Place] ($NODE_ID, id, name, type, url, PartOfPlaceId)
SELECT NODE_ID_FROM_PARTS(object_id('Place'), id) AS node_id,
    id,
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
INSERT INTO [dbo].[Tag] ($NODE_ID, id, name, url, TypeTagClassId)
SELECT NODE_ID_FROM_PARTS(object_id('Tag'), id) AS node_id,
    id,
    name,
    url,
    TypeTagClassId
FROM OPENROWSET (
    BULK ':tag_csv',
    FORMATFILE = '/data/format-files/Tag.xml',
    FIRSTROW = 2
) AS raw;

-- TagClass
INSERT INTO [dbo].[TagClass] ($NODE_ID, id, name, url, SubclassOfTagClassId)
SELECT NODE_ID_FROM_PARTS(object_id('TagClass'), id) AS node_id,
    id,
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
    $NODE_ID,
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
SELECT NODE_ID_FROM_PARTS(object_id('Comment'), id) AS node_id,
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
FROM OPENROWSET (
    BULK ':comment_csv',
    FORMATFILE = '/data/format-files/Comment.xml',
    FIRSTROW = 2
) AS raw;

-- Comment_hasTag_Tag
INSERT INTO [dbo].[Comment_hasTag_Tag] ($FROM_ID, $TO_ID, creationDate, CommentId, TagId)
SELECT  NODE_ID_FROM_PARTS(object_id('Comment'), CommentId) AS from_id,
        NODE_ID_FROM_PARTS(object_id('Tag'), TagId) AS to_id,
       creationDate,
       CommentId,
       TagId
FROM OPENROWSET (
    BULK ':comment_hastag_tag_csv',
    FORMATFILE = '/data/format-files/Comment_hasTag_Tag.xml',
    FIRSTROW = 2
) AS raw;

    
-- Forum
INSERT INTO [dbo].[Forum] ($NODE_ID, creationDate, id, title, ModeratorPersonId)
SELECT NODE_ID_FROM_PARTS(object_id('Forum'), id) AS node_id,
       creationDate,
       id,
       title,
       ModeratorPersonId
FROM OPENROWSET (
    BULK ':forum_csv',
    FORMATFILE = '/data/format-files/Forum.xml',
    FIRSTROW = 2
) AS raw;

-- Forum_hasMember_Person
INSERT INTO [dbo].[Forum_hasMember_Person] ($FROM_ID, $TO_ID, creationDate, ForumId, PersonId)
SELECT  NODE_ID_FROM_PARTS(object_id('Forum'), ForumId) AS from_id,
        NODE_ID_FROM_PARTS(object_id('Person'), PersonId) AS to_id,
        creationDate,
        ForumId,
        PersonId
FROM OPENROWSET (
    BULK ':forum_hasmember_person_csv',
    FORMATFILE = '/data/format-files/Forum_hasMember_Person.xml',
    FIRSTROW = 2
) AS raw;

-- Forum_hasTag_Tag
INSERT INTO [dbo].[Forum_hasTag_Tag] ($FROM_ID, $TO_ID, creationDate, ForumId, TagId)
SELECT NODE_ID_FROM_PARTS(object_id('Forum'), ForumId) AS from_id,
       NODE_ID_FROM_PARTS(object_id('Tag'), TagId) AS to_id,
       creationDate,
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
FROM OPENROWSET (
    BULK ':person_csv',
    FORMATFILE = '/data/format-files/Person.xml',
    FIRSTROW = 2
) AS raw;

-- Person_hasInterest_Tag
INSERT INTO [dbo].[Person_hasInterest_Tag] ($FROM_ID, $TO_ID, creationDate, PersonId, TagId)
SELECT NODE_ID_FROM_PARTS(object_id('Person'), PersonId) AS from_id,
       NODE_ID_FROM_PARTS(object_id('Tag'), TagId) AS to_id,
       creationDate,
       PersonId,
       TagId
FROM OPENROWSET (
    BULK ':person_hasinterest_tag_csv',
    FORMATFILE = '/data/format-files/Person_hasInterest_Tag.xml',
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
    FORMATFILE =  '/data/format-files/Person_knows_Person.xml',
    FIRSTROW = 2
) AS raw;

-- Person_likes_Comment
INSERT INTO [dbo].[Person_likes_Comment] ($FROM_ID, $TO_ID, creationDate, PersonId, CommentId)
SELECT NODE_ID_FROM_PARTS(object_id('Person'), PersonId) AS from_id,
       NODE_ID_FROM_PARTS(object_id('Comment'), CommentId) AS to_id,
       creationDate,
       PersonId,
       CommentId
FROM OPENROWSET (
    BULK ':person_likes_comment_csv',
    FORMATFILE = '/data/format-files/Person_likes_Comment.xml',
    FIRSTROW = 2
) AS raw;

-- Person_likes_Post
INSERT INTO [dbo].[Person_likes_Post] ($FROM_ID, $TO_ID, creationDate, PersonId, PostId)
SELECT NODE_ID_FROM_PARTS(object_id('Person'), PersonId) AS from_id,
       NODE_ID_FROM_PARTS(object_id('Post'), PostId) AS to_id,
       creationDate,
       PersonId,
       PostId
FROM OPENROWSET (
    BULK ':person_likes_post_csv',
    FORMATFILE = '/data/format-files/Person_likes_Post.xml',
    FIRSTROW = 2
) AS raw;

-- Person_studyAt_University
INSERT INTO [dbo].[Person_studyAt_University] ($FROM_ID, $TO_ID, creationDate, PersonId, UniversityId, classYear)
SELECT NODE_ID_FROM_PARTS(object_id('Person'), PersonId) AS from_id,
       NODE_ID_FROM_PARTS(object_id('University'), UniversityId) AS to_id,
       creationDate,
       PersonId,
       UniversityId,
       classYear
FROM OPENROWSET (
    BULK ':person_studyat_university_csv',
    FORMATFILE = '/data/format-files/Person_studyAt_University.xml',
    FIRSTROW = 2
) AS raw;

-- Person_workAt_Company
INSERT INTO [dbo].[Person_workAt_Company] ($FROM_ID, $TO_ID, creationDate, PersonId, CompanyId, workFrom)
SELECT NODE_ID_FROM_PARTS(object_id('Person'), PersonId) AS from_id,
       NODE_ID_FROM_PARTS(object_id('Company'), CompanyId) AS to_id,
       creationDate,
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
    $NODE_ID,
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
SELECT NODE_ID_FROM_PARTS(object_id('Post'), id) AS node_id,
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
FROM OPENROWSET (
    BULK ':post_csv',
    FORMATFILE = '/data/format-files/Post.xml',
    FIRSTROW = 2
) AS raw;

-- Post_hasTag_Tag
INSERT INTO [dbo].[Post_hasTag_Tag] ($FROM_ID, $TO_ID, creationDate, PostId, TagId)
SELECT NODE_ID_FROM_PARTS(object_id('Post'), PostId) AS from_id,
       NODE_ID_FROM_PARTS(object_id('Tag'), TagId) AS to_id,
    creationDate,
    PostId,
    TagId
FROM OPENROWSET (
    BULK ':post_hastag_tag_csv',
    FORMATFILE = '/data/format-files/Post_hasTag_Tag.xml',
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
    id,
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
FROM dbo.post;

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
    CAST(NULL AS varchar(40)) AS imageFile,
    locationIP,
    browserUsed,
    length,
    CAST(NULL AS varchar(40)) AS language,
    CreatorPersonId,
    LocationCountryId,
    CAST(NULL AS bigint) AS ContainerForumId,
    coalesce(ParentPostId, ParentCommentId) AS ParentMessageId
FROM dbo.Comment;
