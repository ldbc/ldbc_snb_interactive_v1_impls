-- Remove post thread
DELETE FROM Message
WHERE MessageId = :postId