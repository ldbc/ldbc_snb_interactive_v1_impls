-- static tables
USE ldbc;
CREATE TABLE Organisation (
    id bigint,
    type varchar(12) NOT NULL,
    name nvarchar(256) NOT NULL,
    url varchar(256) NOT NULL,
    LocationPlaceId bigint NOT NULL
);

CREATE TABLE Place (
    id bigint,
    name nvarchar(256) NOT NULL,
    url varchar(256) NOT NULL,
    type varchar(12) NOT NULL,
    PartOfPlaceId bigint -- null for continents
);

CREATE TABLE Tag (
    id bigint,
    name nvarchar(256) NOT NULL,
    url varchar(256) NOT NULL,
    TypeTagClassId bigint NOT NULL,
    CONSTRAINT PK_Tag PRIMARY KEY NONCLUSTERED ([id] ASC) WITH (DATA_COMPRESSION = PAGE),
    CONSTRAINT Graph_Unique_Key_Tag UNIQUE CLUSTERED ($node_id) WITH (DATA_COMPRESSION = PAGE)
) AS NODE;

CREATE TABLE TagClass (
    id bigint,
    name nvarchar(256) NOT NULL,
    url varchar(256) NOT NULL,
    SubclassOfTagClassId bigint--, -- null for the root TagClass (Thing)
);

-- static tables / separate table per individual subtype
CREATE TABLE Country (
    id bigint,
    name nvarchar(256) not null,
    url varchar(256) not null,
    PartOfContinentId bigint
);

CREATE TABLE City (
    id bigint,
    name nvarchar(256) not null,
    url varchar(256) not null,
    PartOfCountryId bigint
);

CREATE TABLE Company (
    id bigint,
    name nvarchar(256) not null,
    url varchar(256) not null,
    LocationPlaceId bigint not null
);

CREATE TABLE University (
    id bigint,
    name nvarchar(256) not null,
    url varchar(256) not null,
    LocationPlaceId bigint not null
);

-- dynamic tables

CREATE TABLE [dbo].[Forum] (
    [creationDate]      datetimeoffset  NOT NULL,
    [id]                bigint          NOT NULL,
    [title]             nvarchar  (256) NOT NULL,
    [ModeratorPersonId] bigint -- can be null as its cardinality is 0..1
);

CREATE TABLE [dbo].[Person] (
    [creationDate]      datetimeoffset NOT NULL,
    [personId]          BIGINT         NOT NULL,
    [firstName]         nvarchar (MAX) NOT NULL,
    [lastName]          nvarchar (MAX) NOT NULL,
    [gender]            nvarchar (50)  NOT NULL,
    [birthday]          DATETIME       NOT NULL,
    [locationIP]        nvarchar (50)  NOT NULL,
    [browserUsed]       nvarchar (500) NOT NULL,
    [LocationCityId]    BIGINT         NOT NULL,
    [language]          varchar  (640) NOT NULL,
    [email]             varchar  (MAX) NOT NULL
    CONSTRAINT PK_Person PRIMARY KEY NONCLUSTERED ([personId] ASC) WITH (DATA_COMPRESSION = PAGE),
    CONSTRAINT Graph_Unique_Key_Person UNIQUE CLUSTERED ($node_id) WITH (DATA_COMPRESSION = PAGE)
) AS NODE;

-- edges
CREATE TABLE Comment_hasTag_Tag (
    creationDate datetimeoffset NOT NULL,
    CommentId bigint NOT NULL,
    TagId bigint NOT NULL
);

CREATE TABLE Post_hasTag_Tag (
    creationDate datetimeoffset NOT NULL,
    PostId bigint NOT NULL,
    TagId bigint NOT NULL
)


CREATE TABLE Forum_hasMember_Person (
    creationDate datetimeoffset NOT NULL,
    ForumId bigint NOT NULL,
    PersonId bigint NOT NULL
);

CREATE TABLE Forum_hasTag_Tag (
    creationDate datetimeoffset NOT NULL,
    ForumId bigint NOT NULL,
    TagId bigint NOT NULL
);

CREATE TABLE [dbo].[Person_hasInterest_Tag] (
    creationDate datetimeoffset NOT NULL,
    PersonId bigint NOT NULL,
    TagId bigint NOT NULL
);

CREATE TABLE [dbo].[Person_studyAt_University] (
    creationDate datetimeoffset NOT NULL,
    PersonId bigint NOT NULL,
    UniversityId bigint NOT NULL,
    classYear int NOT NULL,
);

CREATE TABLE [dbo].[Person_workAt_Company] (
    creationDate datetimeoffset NOT NULL,
    PersonId bigint NOT NULL,
    CompanyId bigint NOT NULL,
    workFrom int NOT NULL,
);

CREATE TABLE [dbo].[Person_knows_Person] (
    creationDate datetimeoffset NOT NULL,
    [person1Id]     BIGINT        NOT NULL,
    [person2Id]     BIGINT        NOT NULL,
    [weight]     BIGINT        DEFAULT 0,
    INDEX [GRAPH_UNIQUE_INDEX_Person_knows_Person] UNIQUE NONCLUSTERED ($edge_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_FromTo_INDEX_Person_knows_Person] CLUSTERED ($from_id, $to_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_ToFrom_INDEX_Person_knows_Person] NONCLUSTERED ($to_id, $from_id) WITH (DATA_COMPRESSION = PAGE),
) AS EDGE;
ALTER INDEX [GRAPH_UNIQUE_INDEX_Person_knows_Person] ON [dbo].[Person_knows_Person] DISABLE;

-- materialized views
CREATE TABLE Message (
    creationDate datetimeoffset not null,
    MessageId bigint not null,
    content ntext,
    imageFile varchar(80),
    locationIP varchar(80) not null,
    browserUsed varchar(80) not null,
    length int not null,
    language varchar(80),
    CreatorPersonId bigint not null,
    LocationCountryId bigint not null,
    ContainerForumId bigint,
    ParentMessageId bigint,
    ParentPostId bigint,
    ParentCommentId bigint,
    CONSTRAINT PK_Message PRIMARY KEY NONCLUSTERED ([MessageId] ASC) WITH (DATA_COMPRESSION = PAGE),
    CONSTRAINT Graph_Unique_Key_Message UNIQUE CLUSTERED ($node_id) WITH (DATA_COMPRESSION = PAGE)
) AS NODE;

CREATE TABLE Person_likes_Message (
    creationDate datetimeoffset NOT NULL,
    PersonId bigint NOT NULL,
    MessageId bigint NOT NULL,
    INDEX [GRAPH_UNIQUE_INDEX_Person_likes_Message] UNIQUE NONCLUSTERED ($edge_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_FromTo_INDEX_Person_likes_Message] CLUSTERED ($from_id, $to_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_ToFrom_INDEX_Person_likes_Message] NONCLUSTERED ($to_id, $from_id) WITH (DATA_COMPRESSION = PAGE),
) AS EDGE;
ALTER INDEX [GRAPH_UNIQUE_INDEX_Person_likes_Message] ON [dbo].[Person_likes_Message] DISABLE;

CREATE TABLE Message_hasTag_Tag (
    creationDate datetimeoffset NOT NULL,
    MessageId bigint NOT NULL,
    TagId bigint NOT NULL,
    INDEX [GRAPH_UNIQUE_INDEX_Message_hasTag_Tag] UNIQUE NONCLUSTERED ($edge_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_FromTo_INDEX_Message_hasTag_Tag] CLUSTERED ($from_id, $to_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_ToFrom_INDEX_Message_hasTag_Tag] NONCLUSTERED ($to_id, $from_id) WITH (DATA_COMPRESSION = PAGE),
    CONSTRAINT EC_Message_hasTag_Tag CONNECTION (Message TO Tag) ON DELETE CASCADE
) AS EDGE;
ALTER INDEX [GRAPH_UNIQUE_INDEX_Message_hasTag_Tag] ON [dbo].[Message_hasTag_Tag] DISABLE;

CREATE TABLE Message_hasCreator_Person (
    INDEX [GRAPH_UNIQUE_INDEX_Message_hasCreator_Person] UNIQUE NONCLUSTERED ($edge_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_FromTo_INDEX_Message_hasCreator_Person] CLUSTERED ($from_id, $to_id) WITH (DATA_COMPRESSION = PAGE),
    CONSTRAINT EC_Message_hasCreator_Person CONNECTION (Message TO Person) ON DELETE CASCADE
) AS EDGE;
ALTER INDEX [GRAPH_UNIQUE_INDEX_Message_hasCreator_Person] ON [dbo].[Message_hasCreator_Person] DISABLE;

CREATE TABLE Message_replyOf_Message (
    INDEX [GRAPH_UNIQUE_INDEX_Message_replyOf_Message] UNIQUE NONCLUSTERED ($edge_id) WITH (DATA_COMPRESSION = PAGE),
    INDEX [GRAPH_FromTo_INDEX_Message_replyOf_Message] CLUSTERED ($from_id, $to_id) WITH (DATA_COMPRESSION = PAGE),
    CONSTRAINT EC_Message_replyOf_Message CONNECTION (Message TO Message) ON DELETE CASCADE
) AS EDGE;
ALTER INDEX [GRAPH_UNIQUE_INDEX_Message_replyOf_Message] ON [dbo].[Message_replyOf_Message] DISABLE;
