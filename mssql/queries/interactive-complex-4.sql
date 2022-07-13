SELECT TOP(10) name, count(*) AS postCount
        FROM
            Tag,
            Message,
            Message_hasTag_Tag recent,
            Person_knows_Person
        WHERE Message.MessageId = recent.MessageId
        AND recent.TagId = Tag.id
        AND Message.CreatorPersonId = Person2Id
        AND ParentMessageId IS NULL -- post, not comment
        AND Person1Id = :personId
        AND Message.creationDate >= :startDate
           AND Message.creationDate < DATEADD(day, :durationDays, :startDate)
  AND NOT EXISTS (
        SELECT *
        FROM (
            SELECT DISTINCT TagId
            FROM Message, Message_hasTag_Tag, Person_knows_Person
            WHERE Person1Id = :personId
            AND Person2Id = CreatorPersonId
            AND ParentMessageId IS NULL -- post, not comment
            AND Message_hasTag_Tag.MessageId = Message.MessageId
            AND Message.creationDate < :startDate
        ) tags
        WHERE tags.TagId = recent.TagId
      )
GROUP BY name
ORDER BY postCount DESC, name ASC
;
