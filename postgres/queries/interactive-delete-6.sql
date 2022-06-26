-- DEL 6: Remove post thread
DELETE FROM Message
WHERE MessageId = :postId
