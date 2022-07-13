-- Remove comment like
DELETE FROM Person_likes_Message
WHERE PersonId = :personId
  AND MessageId = :commentId