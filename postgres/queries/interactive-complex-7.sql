/* Q7. Recent likers
\set personId 17592186044461
 */
SELECT
    Person.id,
    firstName,
    lastName,
    l.creationDate,
    Message.MessageId,
    coalesce(imageFile, content),
    CAST(floor(EXTRACT(EPOCH FROM (l.creationDate - Message.creationDate))) AS INTEGER) / 60 AS minutesLatency,
    (
        CASE WHEN EXISTS (
            SELECT 1
            FROM Person_knows_Person
            WHERE Person1Id = :personId
              AND Person2Id = Person.id
        ) THEN 0 ELSE 1 END
    ) AS isNew
FROM
    (
        SELECT PersonId, max(Person_likes_Message.creationDate) AS creationDate
        FROM Person_likes_Message, Message
        WHERE Person_likes_Message.MessageId = Message.MessageId
          AND Message.CreatorPersonId = :personId
        GROUP BY PersonId
        ORDER BY creationDate DESC
        LIMIT 20
    ) tmp,
    Message,
    Person,
    Person_likes_Message AS l
WHERE Person.id = tmp.PersonId
  AND tmp.PersonId = l.PersonId
  AND tmp.creationDate = l.creationDate
  AND l.MessageId = Message.MessageId
ORDER BY creationDate DESC, Person.id ASC
;
