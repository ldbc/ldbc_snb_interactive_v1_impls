INSERT INTO Message_replyOf_Message($from_id, $to_id)
VALUES
(
    (SELECT $NODE_ID FROM Message WHERE MessageId = :commentId),
    (SELECT $NODE_ID FROM Message WHERE MessageId = :replyToCommentId + :replyToPostId)
);

INSERT Message_hasCreator_Person ($from_id, $to_id)
VALUES
(
    NODE_ID_FROM_PARTS(object_id('Message'), CAST( :commentId AS BIGINT)),
    NODE_ID_FROM_PARTS(object_id('Person'),  CAST( :authorPersonId AS BIGINT))
);
