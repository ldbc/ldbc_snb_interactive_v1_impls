-- static tables

CREATE TABLE Organisation (
    id bigint PRIMARY KEY,
    type varchar(12) NOT NULL,
    name varchar(256) NOT NULL,
    url varchar(256) NOT NULL,
    LocationPlaceId bigint NOT NULL
) WITH (storage = paged);

CREATE TABLE Place (
    id bigint PRIMARY KEY,
    name varchar(256) NOT NULL,
    url varchar(256) NOT NULL,
    type varchar(12) NOT NULL,
    PartOfPlaceId bigint -- null for continents
) WITH (storage = paged);

CREATE TABLE Tag (
    id bigint PRIMARY KEY,
    name varchar(256) NOT NULL,
    url varchar(256) NOT NULL,
    TypeTagClassId bigint NOT NULL
) WITH (storage = paged);

CREATE TABLE TagClass (
    id bigint PRIMARY KEY,
    name varchar(256) NOT NULL,
    url varchar(256) NOT NULL,
    SubclassOfTagClassId bigint -- null for the root TagClass (Thing)
) WITH (storage = paged);

-- static tables / separate table per individual subtype

CREATE TABLE Country (
    id bigint PRIMARY KEY,
    name varchar(256) NOT NULL,
    url varchar(256) NOT NULL,
    PartOfContinentId bigint
) WITH (storage = paged);

CREATE TABLE City (
    id bigint PRIMARY KEY,
    name varchar(256) NOT NULL,
    url varchar(256) NOT NULL,
    PartOfCountryId bigint
) WITH (storage = paged);

CREATE TABLE Company (
    id bigint PRIMARY KEY,
    name varchar(256) NOT NULL,
    url varchar(256) NOT NULL,
    LocationPlaceId bigint NOT NULL
) WITH (storage = paged);

CREATE TABLE University (
    id bigint PRIMARY KEY,
    name varchar(256) NOT NULL,
    url varchar(256) NOT NULL,
    LocationPlaceId bigint NOT NULL
) WITH (storage = paged);

-- dynamic tables
-- primary key constraints will be added after bulk loading the data

CREATE TABLE Comment (
    creationDate timestamp with time zone NOT NULL,
    id bigint,
    locationIP varchar(80) NOT NULL,
    browserUsed varchar(80) NOT NULL,
    content varchar(2000) NOT NULL,
    length int NOT NULL,
    CreatorPersonId bigint NOT NULL,
    LocationCountryId bigint NOT NULL,
    ParentPostId bigint,
    ParentCommentId bigint
) WITH (storage = paged);

CREATE TABLE Forum (
    creationDate timestamp with time zone NOT NULL,
    id bigint,
    title varchar(256) NOT NULL,
    ModeratorPersonId bigint -- can be null as its cardinality is 0..1
) WITH (storage = paged);

CREATE TABLE Post (
    creationDate timestamp with time zone NOT NULL,
    id bigint,
    imageFile varchar(80),
    locationIP varchar(80) NOT NULL,
    browserUsed varchar(80) NOT NULL,
    language varchar(80),
    content varchar(2000),
    length int NOT NULL,
    CreatorPersonId bigint NOT NULL,
    ContainerForumId bigint NOT NULL,
    LocationCountryId bigint NOT NULL
) WITH (storage = paged);

CREATE TABLE Person (
    creationDate timestamp with time zone NOT NULL,
    id bigint,
    firstName varchar(80) NOT NULL,
    lastName varchar(80) NOT NULL,
    gender varchar(80) NOT NULL,
    birthday date NOT NULL,
    locationIP varchar(80) NOT NULL,
    browserUsed varchar(80) NOT NULL,
    LocationCityId bigint NOT NULL,
    speaks varchar(640) NOT NULL,
    email varchar(8192) NOT NULL
) WITH (storage = paged);

-- dynamic edges

CREATE TABLE Comment_hasTag_Tag (
    creationDate timestamp with time zone NOT NULL,
    CommentId bigint NOT NULL,
    TagId bigint NOT NULL
) WITH (storage = paged);

CREATE TABLE Post_hasTag_Tag (
    creationDate timestamp with time zone NOT NULL,
    PostId bigint NOT NULL,
    TagId bigint NOT NULL
) WITH (storage = paged);

CREATE TABLE Forum_hasMember_Person (
    creationDate timestamp with time zone NOT NULL,
    ForumId bigint NOT NULL,
    PersonId bigint NOT NULL
) WITH (storage = paged);

CREATE TABLE Forum_hasTag_Tag (
    creationDate timestamp with time zone NOT NULL,
    ForumId bigint NOT NULL,
    TagId bigint NOT NULL
) WITH (storage = paged);

CREATE TABLE Person_hasInterest_Tag (
    creationDate timestamp with time zone NOT NULL,
    PersonId bigint NOT NULL,
    TagId bigint NOT NULL
) WITH (storage = paged);

CREATE TABLE Person_likes_Comment (
    creationDate timestamp with time zone NOT NULL,
    PersonId bigint NOT NULL,
    CommentId bigint NOT NULL
) WITH (storage = paged);

CREATE TABLE Person_likes_Post (
    creationDate timestamp with time zone NOT NULL,
    PersonId bigint NOT NULL,
    PostId bigint NOT NULL
) WITH (storage = paged);

CREATE TABLE Person_studyAt_University (
    creationDate timestamp with time zone NOT NULL,
    PersonId bigint NOT NULL,
    UniversityId bigint NOT NULL,
    classYear int NOT NULL
) WITH (storage = paged);

CREATE TABLE Person_workAt_Company (
    creationDate timestamp with time zone NOT NULL,
    PersonId bigint NOT NULL,
    CompanyId bigint NOT NULL,
    workFrom int NOT NULL
) WITH (storage = paged);

CREATE TABLE Person_knows_Person (
    creationDate timestamp with time zone NOT NULL,
    Person1Id bigint NOT NULL,
    Person2Id bigint NOT NULL
) WITH (storage = paged);


-- materialized views for Messages and their edges (likes: incoming, hasTag: outgoing)

CREATE TABLE Message (
    creationDate timestamp with time zone NOT NULL,
    id bigint,
    language varchar(80),
    content varchar(2000),
    imageFile varchar(80),
    locationIP varchar(80) NOT NULL,
    browserUsed varchar(80) NOT NULL,
    length int NOT NULL,
    CreatorPersonId bigint NOT NULL,
    ContainerForumId bigint,
    LocationCountryId bigint NOT NULL,
    ParentMessageId bigint
) WITH (storage = paged);

CREATE TABLE Person_likes_Message (
    creationDate timestamp with time zone NOT NULL,
    PersonId bigint NOT NULL,
    id bigint NOT NULL
) WITH (storage = paged);

CREATE TABLE Message_hasTag_Tag (
    creationDate timestamp with time zone NOT NULL,
    id bigint NOT NULL,
    TagId bigint NOT NULL
) WITH (storage = paged);
