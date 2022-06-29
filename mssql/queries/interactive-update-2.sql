INSERT Person_likes_Message ($from_id, $to_id, Person1id, Person2id, k_creationDate)
VALUES
(
    NODE_ID_FROM_PARTS(object_id('Person'), :personId),
    NODE_ID_FROM_PARTS(object_id('Post'), :postId),
    :personId,
    :postId,
    :creationDate
);
