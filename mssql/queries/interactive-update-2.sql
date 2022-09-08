INSERT INTO Person_likes_Message (
    $from_id
  , $to_id
  , creationDate
  , PersonId
  , MessageId
)
VALUES
(
    NODE_ID_FROM_PARTS(object_id('Person'),  CAST( :personId AS BIGINT))
  , NODE_ID_FROM_PARTS(object_id('Message'), CAST( :postId AS BIGINT))
  , :creationDate
  , :personId
  , :postId
);
