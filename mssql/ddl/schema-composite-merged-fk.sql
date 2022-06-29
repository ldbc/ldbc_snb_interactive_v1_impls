-- static tables
USE ldbc;
CREATE TABLE Organisation (
    id bigint,
    type varchar(12) NOT NULL,
    name nvarchar(256) NOT NULL,
    url varchar(256) NOT NULL,
    LocationPlaceId bigint NOT NULL
    CONSTRAINT PK_Organisation PRIMARY KEY NONCLUSTERED ([id] ASC) WITH (DATA_COMPRESSION = PAGE),
    CONSTRAINT Graph_Unique_Key_Organisation UNIQUE CLUSTERED ($node_id) WITH (DATA_COMPRESSION = PAGE)
) AS NODE;

CREATE TABLE Place (
    id bigint,
    name nvarchar(256) NOT NULL,
    url varchar(256) NOT NULL,
    type varchar(12) NOT NULL,
    PartOfPlaceId bigint -- null for continents
    CONSTRAINT PK_Place PRIMARY KEY NONCLUSTERED ([id] ASC) WITH (DATA_COMPRESSION = PAGE),
    CONSTRAINT Graph_Unique_Key_Place UNIQUE CLUSTERED ($node_id) WITH (DATA_COMPRESSION = PAGE)
) AS NODE;

CREATE TABLE Tag (
    id bigint,
    name nvarchar(256) NOT NULL,
    url varchar(256) NOT NULL,
    TypeTagClassId bigint NOT NULL
    CONSTRAINT PK_Tag PRIMARY KEY NONCLUSTERED ([id] ASC) WITH (DATA_COMPRESSION = PAGE),
    CONSTRAINT Graph_Unique_Key_Tag UNIQUE CLUSTERED ($node_id) WITH (DATA_COMPRESSION = PAGE)
) AS NODE;

CREATE TABLE TagClass (
    id bigint,
    name nvarchar(256) NOT NULL,
    url varchar(256) NOT NULL,
    SubclassOfTagClassId bigint -- null for the root TagClass (Thing)
    CONSTRAINT PK_TagClass PRIMARY KEY NONCLUSTERED ([id] ASC) WITH (DATA_COMPRESSION = PAGE),
    CONSTRAINT Graph_Unique_Key_TagClass UNIQUE CLUSTERED ($node_id) WITH (DATA_COMPRESSION = PAGE)
) AS NODE;

-- static tables / separate table per individual subtype
CREATE TABLE Country (
    id bigint,
    name nvarchar(256) not null,
    url varchar(256) not null,
    PartOfContinentId bigint
    CONSTRAINT PK_Country PRIMARY KEY NONCLUSTERED ([id] ASC) WITH (DATA_COMPRESSION = PAGE),
    CONSTRAINT Graph_Unique_Key_Country UNIQUE CLUSTERED ($node_id) WITH (DATA_COMPRESSION = PAGE)
) AS NODE;

CREATE TABLE City (
    id bigint,
    name nvarchar(256) not null,
    url varchar(256) not null,
    PartOfCountryId bigint
    CONSTRAINT PK_City PRIMARY KEY NONCLUSTERED ([id] ASC) WITH (DATA_COMPRESSION = PAGE),
    CONSTRAINT Graph_Unique_Key_City UNIQUE CLUSTERED ($node_id) WITH (DATA_COMPRESSION = PAGE)
) AS NODE;

CREATE TABLE Company (
    id bigint,
    name nvarchar(256) not null,
    url varchar(256) not null,
    LocationPlaceId bigint not null
    CONSTRAINT PK_Company PRIMARY KEY NONCLUSTERED ([id] ASC) WITH (DATA_COMPRESSION = PAGE),
    CONSTRAINT Graph_Unique_Key_Company UNIQUE CLUSTERED ($node_id) WITH (DATA_COMPRESSION = PAGE)
) AS NODE;

CREATE TABLE University (
    id bigint,
    name nvarchar(256) not null,
    url varchar(256) not null,
    LocationPlaceId bigint not null
    CONSTRAINT PK_University PRIMARY KEY NONCLUSTERED ([id] ASC) WITH (DATA_COMPRESSION = PAGE),
    CONSTRAINT Graph_Unique_Key_University UNIQUE CLUSTERED ($node_id) WITH (DATA_COMPRESSION = PAGE)
) AS NODE;

-- dynamic tables
CREATE TABLE Comment (
    creationDate datetimeoffset NOT NULL,
    id bigint,
    locationIP varchar(40) NOT NULL,
    browserUsed varchar(40) NOT NULL,
    content ntext NOT NULL,
    length int NOT NULL,
    CreatorPersonId bigint NOT NULL,
    LocationCountryId bigint NOT NULL,
    ParentPostId bigint,
    ParentCommentId bigint
    CONSTRAINT PK_Comment PRIMARY KEY NONCLUSTERED ([id] ASC) WITH (DATA_COMPRESSION = PAGE),
    CONSTRAINT Graph_Unique_Key_Comment UNIQUE CLUSTERED ($node_id) WITH (DATA_COMPRESSION = PAGE)
) AS NODE;

CREATE TABLE Forum (
    creationDate datetimeoffset NOT NULL,
    id bigint NOT NULL,
    title nvarchar(256) NOT NULL,
    ModeratorPersonId bigint -- can be null as its cardinality is 0..1
    CONSTRAINT PK_Forum PRIMARY KEY NONCLUSTERED ([id] ASC) WITH (DATA_COMPRESSION = PAGE),
    CONSTRAINT Graph_Unique_Key_Forum UNIQUE CLUSTERED ($node_id) WITH (DATA_COMPRESSION = PAGE)
) AS NODE;

CREATE TABLE Post (
    creationDate datetimeoffset NOT NULL,
    id bigint NOT NULL,
    imageFile varchar(40),
    locationIP varchar(40) NOT NULL,
    browserUsed varchar(40) NOT NULL,
    language varchar(40),
    content ntext,
    length int NOT NULL,
    CreatorPersonId bigint NOT NULL,
    ContainerForumId bigint NOT NULL,
    LocationCountryId bigint NOT NULL
    CONSTRAINT PK_Post PRIMARY KEY NONCLUSTERED ([id] ASC) WITH (DATA_COMPRESSION = PAGE),
    CONSTRAINT Graph_Unique_Key_Post UNIQUE CLUSTERED ($node_id) WITH (DATA_COMPRESSION = PAGE)
) AS NODE;

CREATE TABLE [dbo].[Person] (
    [creationDate] datetimeoffset      NOT NULL,
    [personId]     BIGINT        NOT NULL,
    [firstName]    nvarchar (MAX) NOT NULL,
    [lastName]     nvarchar (MAX) NOT NULL,
    [gender]       nvarchar (50) NOT NULL,
    [birthday]     DATETIME      NOT NULL,
    [locationIP]   nvarchar (50) NOT NULL,
    [browserUsed]  nvarchar (500) NOT NULL,
    [LocationCityId]    BIGINT NOT NULL,
    [language] varchar(640) NOT NULL,
    [email] varchar(MAX) NOT NULL
    CONSTRAINT PK_Person PRIMARY KEY NONCLUSTERED ([personId] ASC) WITH (DATA_COMPRESSION = PAGE),
    CONSTRAINT Graph_Unique_Key_Person UNIQUE CLUSTERED ($node_id) WITH (DATA_COMPRESSION = PAGE)
) AS NODE;

-- edges
CREATE TABLE Comment_hasTag_Tag (
    creationDate datetimeoffset NOT NULL,
    CommentId bigint NOT NULL,
    TagId bigint NOT NULL
    INDEX [GRAPH_UNIQUE_INDEX_Comment_hasTag_Tag] UNIQUE NONCLUSTERED ($edge_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_FromTo_INDEX_Comment_hasTag_Tag] CLUSTERED ($from_id, $to_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_ToFrom_INDEX_Comment_hasTag_Tag] NONCLUSTERED ($to_id, $from_id) WITH (DATA_COMPRESSION = PAGE)
) AS EDGE;
ALTER INDEX [GRAPH_UNIQUE_INDEX_Comment_hasTag_Tag] ON [dbo].[Comment_hasTag_Tag] DISABLE;

CREATE TABLE Post_hasTag_Tag (
    creationDate datetimeoffset NOT NULL,
    PostId bigint NOT NULL,
    TagId bigint NOT NULL
    INDEX [GRAPH_UNIQUE_INDEX_Post_hasTag_Tag] UNIQUE NONCLUSTERED ($edge_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_FromTo_INDEX_Post_hasTag_Tag] CLUSTERED ($from_id, $to_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_ToFrom_INDEX_Post_hasTag_Tag] NONCLUSTERED ($to_id, $from_id) WITH (DATA_COMPRESSION = PAGE)
) AS EDGE;
ALTER INDEX [GRAPH_UNIQUE_INDEX_Post_hasTag_Tag] ON [dbo].[Post_hasTag_Tag] DISABLE;


CREATE TABLE Forum_hasMember_Person (
    creationDate datetimeoffset NOT NULL,
    ForumId bigint NOT NULL,
    PersonId bigint NOT NULL
    INDEX [GRAPH_UNIQUE_INDEX_Forum_hasMember_Person] UNIQUE NONCLUSTERED ($edge_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_FromTo_INDEX_Forum_hasMember_Person] CLUSTERED ($from_id, $to_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_ToFrom_INDEX_Forum_hasMember_Person] NONCLUSTERED ($to_id, $from_id) WITH (DATA_COMPRESSION = PAGE)
) AS EDGE;
ALTER INDEX [GRAPH_UNIQUE_INDEX_Forum_hasMember_Person] ON [dbo].[Forum_hasMember_Person] DISABLE;

CREATE TABLE Forum_hasTag_Tag (
    creationDate datetimeoffset NOT NULL,
    ForumId bigint NOT NULL,
    TagId bigint NOT NULL
    INDEX [GRAPH_UNIQUE_INDEX_Forum_hasTag_Tag] UNIQUE NONCLUSTERED ($edge_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_FromTo_INDEX_Forum_hasTag_Tag] CLUSTERED ($from_id, $to_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_ToFrom_INDEX_Forum_hasTag_Tag] NONCLUSTERED ($to_id, $from_id) WITH (DATA_COMPRESSION = PAGE)
) AS EDGE;
ALTER INDEX [GRAPH_UNIQUE_INDEX_Forum_hasTag_Tag] ON [dbo].[Forum_hasTag_Tag] DISABLE;


CREATE TABLE [dbo].[Person_hasInterest_Tag] (
    creationDate datetimeoffset NOT NULL,
    PersonId bigint NOT NULL,
    TagId bigint NOT NULL
    INDEX [GRAPH_UNIQUE_INDEX_Person_hasInterest_Tag] UNIQUE NONCLUSTERED ($edge_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_FromTo_INDEX_Person_hasInterest_Tag] CLUSTERED ($from_id, $to_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_ToFrom_INDEX_Person_hasInterest_Tag] NONCLUSTERED ($to_id, $from_id) WITH (DATA_COMPRESSION = PAGE)
) AS EDGE;
ALTER INDEX [GRAPH_UNIQUE_INDEX_Person_hasInterest_Tag] ON [dbo].[Person_hasInterest_Tag] DISABLE;

CREATE TABLE [dbo].[Person_likes_Comment] (
    creationDate datetimeoffset NOT NULL,
    PersonId bigint NOT NULL,
    CommentId bigint NOT NULL
    INDEX [GRAPH_UNIQUE_INDEX_Person_likes_Comment] UNIQUE NONCLUSTERED ($edge_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_FromTo_INDEX_Person_likes_Comment] CLUSTERED ($from_id, $to_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_ToFrom_INDEX_Person_likes_Comment] NONCLUSTERED ($to_id, $from_id) WITH (DATA_COMPRESSION = PAGE)
) AS EDGE;
ALTER INDEX [GRAPH_UNIQUE_INDEX_Person_likes_Comment] ON [dbo].[Person_likes_Comment] DISABLE;

CREATE TABLE [dbo].[Person_likes_Post] (
    creationDate datetimeoffset NOT NULL,
    PersonId bigint NOT NULL,
    PostId bigint NOT NULL,
    INDEX [GRAPH_UNIQUE_INDEX_Person_likes_Post] UNIQUE NONCLUSTERED ($edge_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_FromTo_INDEX_Person_likes_Post] CLUSTERED ($from_id, $to_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_ToFrom_INDEX_Person_likes_Post] NONCLUSTERED ($to_id, $from_id) WITH (DATA_COMPRESSION = PAGE)
) AS EDGE;
ALTER INDEX [GRAPH_UNIQUE_INDEX_Person_likes_Post] ON [dbo].[Person_likes_Post] DISABLE;

CREATE TABLE [dbo].[Person_studyAt_University] (
    creationDate datetimeoffset NOT NULL,
    PersonId bigint NOT NULL,
    UniversityId bigint NOT NULL,
    classYear int NOT NULL,
    INDEX [GRAPH_UNIQUE_INDEX_Person_studyAt_University] UNIQUE NONCLUSTERED ($edge_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_FromTo_INDEX_Person_studyAt_University] CLUSTERED ($from_id, $to_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_ToFrom_INDEX_Person_studyAt_University] NONCLUSTERED ($to_id, $from_id) WITH (DATA_COMPRESSION = PAGE)
) AS EDGE;
ALTER INDEX [GRAPH_UNIQUE_INDEX_Person_studyAt_University] ON [dbo].[Person_studyAt_University] DISABLE;

CREATE TABLE [dbo].[Person_workAt_Company] (
    creationDate datetimeoffset NOT NULL,
    PersonId bigint NOT NULL,
    CompanyId bigint NOT NULL,
    workFrom int NOT NULL,
    INDEX [GRAPH_UNIQUE_INDEX_Person_workAt_Company] UNIQUE NONCLUSTERED ($edge_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_FromTo_INDEX_Person_workAt_Company] CLUSTERED ($from_id, $to_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_ToFrom_INDEX_Person_workAt_Company] NONCLUSTERED ($to_id, $from_id) WITH (DATA_COMPRESSION = PAGE)
) AS EDGE;
ALTER INDEX [GRAPH_UNIQUE_INDEX_Person_workAt_Company] ON [dbo].[Person_workAt_Company] DISABLE;

CREATE TABLE [dbo].[Person_knows_Person] (
    creationDate datetimeoffset NOT NULL,
    [person1Id]     BIGINT        NOT NULL,
    [person2Id]     BIGINT        NOT NULL,
    INDEX [GRAPH_UNIQUE_INDEX_Person_knows_Person] UNIQUE NONCLUSTERED ($edge_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_FromTo_INDEX_Person_knows_Person] CLUSTERED ($from_id, $to_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_ToFrom_INDEX_Person_knows_Person] NONCLUSTERED ($to_id, $from_id) WITH (DATA_COMPRESSION = PAGE)
) AS EDGE;
ALTER INDEX [GRAPH_UNIQUE_INDEX_Person_knows_Person] ON [dbo].[Person_knows_Person] DISABLE;

-- materialized views

-- A recursive materialized view containing the root Post of each Message (for Posts, themselves, for Comments, traversing up the Message thread to the root Post of the tree)
CREATE TABLE Message (
    creationDate datetimeoffset not null,
    MessageId bigint not null,
    RootPostId bigint not null,
    RootPostLanguage varchar(40),
    content ntext,
    imageFile varchar(40),
    locationIP varchar(40) not null,
    browserUsed varchar(40) not null,
    length int not null,
    CreatorPersonId bigint not null,
    ContainerForumId bigint,
    LocationCountryId bigint not null,
    ParentMessageId bigint,
    ParentPostId bigint,
    ParentCommentId bigint,
    type varchar(10)
    CONSTRAINT PK_Message PRIMARY KEY NONCLUSTERED ([MessageId] ASC) WITH (DATA_COMPRESSION = PAGE),
    CONSTRAINT Graph_Unique_Key_Message UNIQUE CLUSTERED ($node_id) WITH (DATA_COMPRESSION = PAGE)
) AS NODE;

CREATE TABLE Person_likes_Message (
    creationDate datetimeoffset NOT NULL,
    PersonId bigint NOT NULL,
    MessageId bigint NOT NULL
    INDEX [GRAPH_UNIQUE_INDEX_Person_knows_Person] UNIQUE NONCLUSTERED ($edge_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_FromTo_INDEX_Person_knows_Person] CLUSTERED ($from_id, $to_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_ToFrom_INDEX_Person_knows_Person] NONCLUSTERED ($to_id, $from_id) WITH (DATA_COMPRESSION = PAGE)
) AS EDGE;
ALTER INDEX [GRAPH_UNIQUE_INDEX_Person_knows_Person] ON [dbo].[Person_knows_Person] DISABLE;

CREATE TABLE Message_hasTag_Tag (
    creationDate datetimeoffset NOT NULL,
    MessageId bigint NOT NULL,
    TagId bigint NOT NULL
    INDEX [GRAPH_UNIQUE_INDEX_Message_hasTag_Tag] UNIQUE NONCLUSTERED ($edge_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_FromTo_INDEX_Message_hasTag_Tag] CLUSTERED ($from_id, $to_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_ToFrom_INDEX_Message_hasTag_Tag] NONCLUSTERED ($to_id, $from_id) WITH (DATA_COMPRESSION = PAGE)
) AS EDGE;
ALTER INDEX [GRAPH_UNIQUE_INDEX_Message_hasTag_Tag] ON [dbo].[Message_hasTag_Tag] DISABLE;

-- views
CREATE VIEW Comment_View AS
    SELECT creationDate, MessageId AS id, locationIP, browserUsed, content, length, CreatorPersonId, LocationCountryId, ParentPostId, ParentCommentId
    FROM Message
    WHERE ParentMessageId IS NOT NULL;

CREATE VIEW Post_View AS
    SELECT creationDate, MessageId AS id, imageFile, locationIP, browserUsed, RootPostLanguage, content, length, CreatorPersonId, ContainerForumId, LocationCountryId
    From Message
    WHERE ParentMessageId IS NULL;
