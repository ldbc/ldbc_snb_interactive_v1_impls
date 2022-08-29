SELECT TOP(10) name, count(*) AS postcount
FROM
    Tag,
    Message_hasTag_Tag,
    Message,
    (
        SELECT Person2Id
        FROM Person_knows_Person
        WHERE Person1Id = :personId
        UNION
        SELECT k2.Person2Id
        FROM Person_knows_Person k1, Person_knows_Person k2
        WHERE k1.Person1Id = :personId
          AND k1.Person2Id = k2.Person1Id
          AND k2.Person2Id <> :personId
    ) friend
WHERE CreatorPersonId = friend.Person2Id
  AND ParentMessageId IS NULL -- post, not comment
  AND Message.MessageId = Message_hasTag_Tag.MessageId
  AND Message_hasTag_Tag.TagId = Tag.id
  AND name <> :tagName
  AND EXISTS (
          SELECT *
          FROM Tag, Message_hasTag_Tag
          WHERE Message.MessageId = Message_hasTag_Tag.MessageId
            AND Message_hasTag_Tag.TagId = Tag.id
            AND Tag.name = :tagName
      )
GROUP BY name
ORDER BY postCount DESC, name COLLATE Latin1_General_BIN2;  
;
