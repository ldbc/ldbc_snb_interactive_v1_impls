-- maintain materialized views

-- Comments attaching to existing Message trees
INSERT INTO Message
    WITH RECURSIVE Message_CTE(id, RootPostId, RootPostLanguage, ContainerForumId, ParentMessageId) AS (
        -- first half of the union: Comments attaching directly to the existing tree
        SELECT
            Comment.id AS id,
            Message.RootPostId AS RootPostId,
            Message.RootPostLanguage AS RootPostLanguage,
            Message.ContainerForumId AS ContainerForumId,
            coalesce(Comment.ParentPostId, Comment.ParentCommentId) AS ParentMessageId
        FROM Comment
        JOIN Message
          ON Message.id = coalesce(Comment.ParentPostId, Comment.ParentCommentId)
        UNION ALL
        -- second half of the union: Comments attaching newly inserted Comments
        SELECT
            Comment.id AS id,
            Message_CTE.RootPostId AS RootPostId,
            Message_CTE.RootPostLanguage AS RootPostLanguage,
            Message_CTE.ContainerForumId AS ContainerForumId,
            Comment.ParentCommentId AS ParentMessageId
        FROM Comment
        JOIN Message_CTE
          ON FORCEORDER(Comment.ParentCommentId = Message_CTE.id)
    )
    SELECT
        Comment.creationDate AS creationDate,
        Comment.id AS id,
        Message_CTE.RootPostId AS RootPostId,
        Message_CTE.RootPostLanguage AS RootPostLanguage,
        Comment.content AS content,
        NULL::text AS imageFile,
        Comment.locationIP AS locationIP,
        Comment.browserUsed AS browserUsed,
        Comment.length AS length,
        Comment.CreatorPersonId AS CreatorPersonId,
        Message_CTE.ContainerForumId AS ContainerForumId,
        Comment.LocationCountryId AS LocationCityId,
        coalesce(Comment.ParentPostId, Comment.ParentCommentId) AS ParentMessageId
    FROM Message_CTE
    JOIN Comment
      ON FORCEORDER(Message_CTE.id = Comment.id)
;

DROP TABLE IF EXISTS Comment;

----------------------------------------------------------------------------------------------------
------------------------------------------- CLEANUP ------------------------------------------------
----------------------------------------------------------------------------------------------------

CREATE TABLE Comment (
    creationDate timestamp with time zone NOT NULL,
    id bigint NOT NULL, --PRIMARY KEY,
    locationIP text NOT NULL,
    browserUsed text NOT NULL,
    content text NOT NULL,
    length int NOT NULL,
    CreatorPersonId bigint NOT NULL,
    LocationCountryId bigint NOT NULL,
    ParentPostId bigint,
    ParentCommentId bigint
) WITH (storage = paged);
