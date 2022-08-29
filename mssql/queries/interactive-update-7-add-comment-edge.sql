INSERT INTO Message_replyOf_Message($from_id, $to_id)
VALUES
(
    (SELECT $NODE_ID FROM Message WHERE MessageId = :commentId),
    (SELECT $NODE_ID FROM Message WHERE MessageId = :replyToCommentId + :replyToPostId)
);