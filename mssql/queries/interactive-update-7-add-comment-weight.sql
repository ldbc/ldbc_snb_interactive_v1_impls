UPDATE Person_knows_Person
SET weight = weight + 1
WHERE Person1Id = :authorPersonId 
  AND Person2Id = (SELECT CreatorPersonId FROM Message WHERE MessageId = :replyToCommentId + :replyToPostId);