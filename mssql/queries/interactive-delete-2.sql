-- Remove post like
DELETE FROM Person_likes_Message
WHERE PersonId = :personId
  AND MessageId = :postId