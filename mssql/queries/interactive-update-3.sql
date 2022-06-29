INSERT Person_likes_Message ($from_id, $to_id, creationDate, PersonId, CommentId)
VALUES
(
    NODE_ID_FROM_PARTS(object_id('Person'), :personId),
    NODE_ID_FROM_PARTS(object_id('Post'), :commentId),
    :creationDate,
    :personId,
    :commentId
);
