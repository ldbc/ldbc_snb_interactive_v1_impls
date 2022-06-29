INSERT Forum_hasMember_Person ($from_id, $to_id, creationDate, ForumId, PersonId)
VALUES
(
    NODE_ID_FROM_PARTS(object_id('Person'), :forumId),
    NODE_ID_FROM_PARTS(object_id('Post'), :personId),
    :creationDate,
    :ForumId,
    :PersonId
);
