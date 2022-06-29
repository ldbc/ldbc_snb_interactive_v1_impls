-- Remove comment subthread
DELETE FROM Message
WHERE MessageId = :commentId