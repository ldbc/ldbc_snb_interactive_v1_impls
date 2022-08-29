INSERT INTO Person_likes_Message (
    $from_id
  , $to_id
  , creationDate
  , PersonId
  , MessageId
)
VALUES
(
    (SELECT $NODE_ID FROM Person WHERE personId = :personId),
    (SELECT $NODE_ID FROM Message WHERE MessageId = :commentId),
    :creationDate
  , :personId
  , :commentId
);
