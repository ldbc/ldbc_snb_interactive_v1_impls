SELECT
    Person.personId,
    firstName,
    lastName,
    l.creationDate,
    Message.MessageId,
    coalesce(imageFile, content)
     , DATEDIFF_BIG(second, Message.creationDate, l.creationDate)/60 AS minutesLatency
     , (CASE WHEN EXISTS (SElECT 1
                            FROM Person_knows_Person
                           WHERE Person1Id = :personId
                             AND Person2Id = Person.personId)
                            THEN 0 ELSE 1 END
       ) AS isNew
FROM
    (
        SELECT TOP(20) PersonId, max(Person_likes_Message.creationDate) AS creationDate
        FROM Person_likes_Message, Message
        WHERE
          Person_likes_Message.MessageId = Message.MessageId AND
          CreatorPersonId = :personId
        GROUP BY PersonId
        ORDER BY creationDate DESC
    ) tmp,
    Message,
    Person,
    Person_likes_Message AS l
WHERE Person.personId = tmp.PersonId
  AND tmp.PersonId = l.PersonId
  AND tmp.creationDate = l.creationDate
  AND l.MessageId = Message.MessageId
ORDER BY creationDate DESC, Person.personId ASC
;
