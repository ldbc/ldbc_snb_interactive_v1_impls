INSERT INTO Message_replyOf_Message($from_id, $to_id)
VALUES
(
    (SELECT $NODE_ID FROM Message WHERE MessageId = :commentId),
    (SELECT $NODE_ID FROM Message WHERE MessageId = :replyToCommentId + :replyToPostId)
);

INSERT INTO Message_hasCreator_Person($from_id, $to_id)
VALUES
(
    (SELECT $NODE_ID FROM Message WHERE MessageId = :commentId),
    (SELECT $NODE_ID FROM Person WHERE personId = :authorPersonId)
);