-- static tables

CREATE TABLE Organisation (
    id bigint PRIMARY KEY,
    type varchar(12) NOT NULL,
    name varchar(256) NOT NULL,
    url varchar(256) NOT NULL,
    LocationPlaceId bigint NOT NULL
);

CREATE TABLE Place (
    id bigint PRIMARY KEY,
    name varchar(256) NOT NULL,
    url varchar(256) NOT NULL,
    type varchar(12) NOT NULL,
    PartOfPlaceId bigint -- null for continents
);

CREATE TABLE Tag (
    id bigint PRIMARY KEY,
    name varchar(256) NOT NULL,
    url varchar(256) NOT NULL,
    TypeTagClassId bigint NOT NULL
);

CREATE TABLE TagClass (
    id bigint PRIMARY KEY,
    name varchar(256) NOT NULL,
    url varchar(256) NOT NULL,
    SubclassOfTagClassId bigint -- null for the root TagClass (Thing)
);

-- static tables / separate table per individual subtype

CREATE TABLE Country (
    id bigint primary key,
    name varchar(256) not null,
    url varchar(256) not null,
    PartOfContinentId bigint
);

CREATE TABLE City (
    id bigint primary key,
    name varchar(256) not null,
    url varchar(256) not null,
    PartOfCountryId bigint
);

CREATE TABLE Company (
    id bigint primary key,
    name varchar(256) not null,
    url varchar(256) not null,
    LocationPlaceId bigint not null
);

CREATE TABLE University (
    id bigint primary key,
    name varchar(256) not null,
    url varchar(256) not null,
    LocationPlaceId bigint not null
);

-- dynamic tables

CREATE TABLE Comment (
    creationDate timestamp with time zone NOT NULL,
    id bigint PRIMARY KEY,
    locationIP varchar(40) NOT NULL,
    browserUsed varchar(40) NOT NULL,
    content varchar(2000) NOT NULL,
    length int NOT NULL,
    CreatorPersonId bigint NOT NULL,
    LocationCountryId bigint NOT NULL,
    ParentPostId bigint,
    ParentCommentId bigint
);

CREATE TABLE Forum (
    creationDate timestamp with time zone NOT NULL,
    id bigint PRIMARY KEY,
    title varchar(256) NOT NULL,
    ModeratorPersonId bigint -- can be null as its cardinality is 0..1
);

CREATE TABLE Post (
    creationDate timestamp with time zone NOT NULL,
    id bigint PRIMARY KEY,
    imageFile varchar(40),
    locationIP varchar(40) NOT NULL,
    browserUsed varchar(40) NOT NULL,
    language varchar(40),
    content varchar(2000),
    length int NOT NULL,
    CreatorPersonId bigint NOT NULL,
    ContainerForumId bigint NOT NULL,
    LocationCountryId bigint NOT NULL
);

CREATE TABLE Person (
    creationDate timestamp with time zone NOT NULL,
    id bigint PRIMARY KEY,
    firstName varchar(40) NOT NULL,
    lastName varchar(40) NOT NULL,
    gender varchar(40) NOT NULL,
    birthday date NOT NULL,
    locationIP varchar(40) NOT NULL,
    browserUsed varchar(40) NOT NULL,
    LocationCityId bigint NOT NULL,
    speaks varchar(640) NOT NULL,
    email varchar(8192) NOT NULL
);


-- edges
CREATE TABLE Comment_hasTag_Tag (
    creationDate timestamp with time zone NOT NULL,
    CommentId bigint NOT NULL,
    TagId bigint NOT NULL
    --, PRIMARY KEY(CommentId, TagId)
);

CREATE TABLE Post_hasTag_Tag (
    creationDate timestamp with time zone NOT NULL,
    PostId bigint NOT NULL,
    TagId bigint NOT NULL
    --, PRIMARY KEY(PostId, TagId)
);

CREATE TABLE Forum_hasMember_Person (
    creationDate timestamp with time zone NOT NULL,
    ForumId bigint NOT NULL,
    PersonId bigint NOT NULL
    --, PRIMARY KEY(ForumId, PersonId)
);

CREATE TABLE Forum_hasTag_Tag (
    creationDate timestamp with time zone NOT NULL,
    ForumId bigint NOT NULL,
    TagId bigint NOT NULL
    --, PRIMARY KEY(ForumId, TagId)
);

CREATE TABLE Person_hasInterest_Tag (
    creationDate timestamp with time zone NOT NULL,
    PersonId bigint NOT NULL,
    TagId bigint NOT NULL
    --, PRIMARY KEY(PersonId, TagId)
);

CREATE TABLE Person_likes_Comment (
    creationDate timestamp with time zone NOT NULL,
    PersonId bigint NOT NULL,
    CommentId bigint NOT NULL
    --, PRIMARY KEY(PersonId, CommentId)
);

CREATE TABLE Person_likes_Post (
    creationDate timestamp with time zone NOT NULL,
    PersonId bigint NOT NULL,
    PostId bigint NOT NULL
    --, PRIMARY KEY(PersonId, PostId)
);

CREATE TABLE Person_studyAt_University (
    creationDate timestamp with time zone NOT NULL,
    PersonId bigint NOT NULL,
    UniversityId bigint NOT NULL,
    classYear int NOT NULL
    --, PRIMARY KEY(PersonId, UniversityId)
);

CREATE TABLE Person_workAt_Company (
    creationDate timestamp with time zone NOT NULL,
    PersonId bigint NOT NULL,
    CompanyId bigint NOT NULL,
    workFrom int NOT NULL
    --, PRIMARY KEY(PersonId, CompanyId)
);

CREATE TABLE Person_knows_Person (
    creationDate timestamp with time zone NOT NULL,
    Person1id bigint NOT NULL,
    Person2id bigint NOT NULL
    --, PRIMARY KEY(Person1id, Person2id)
);


-- materialized views

-- A recursive materialized view containing the root Post of each Message (for Posts, themselves, for Comments, traversing up the Message thread to the root Post of the tree)
CREATE TABLE Message (
    creationDate timestamp with time zone not null,
    MessageId bigint primary key,
    RootPostId bigint not null,
    RootPostLanguage varchar(40),
    content varchar(2000),
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
    type varchar(7)
);

CREATE TABLE Person_likes_Message (
    creationDate timestamp with time zone NOT NULL,
    PersonId bigint NOT NULL,
    MessageId bigint NOT NULL
);

CREATE TABLE Message_hasTag_Tag (
    creationDate timestamp with time zone NOT NULL,
    MessageId bigint NOT NULL,
    TagId bigint NOT NULL
);

-- views

CREATE VIEW Comment_View AS
    SELECT creationDate, MessageId AS id, locationIP, browserUsed, content, length, CreatorPersonId, LocationCountryId, ParentPostId, ParentCommentId
    FROM Message
    WHERE ParentMessageId IS NOT NULL;

CREATE VIEW Post_View AS
    SELECT creationDate, MessageId AS id, imageFile, locationIP, browserUsed, RootPostLanguage, content, length, CreatorPersonId, ContainerForumId, LocationCountryId
    From Message
    WHERE ParentMessageId IS NULL;
