-- DEL 7: Remove comment subthread
DELETE FROM Message
WHERE MessageId = :commentId
