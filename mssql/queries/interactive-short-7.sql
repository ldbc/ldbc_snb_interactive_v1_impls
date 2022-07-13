/* IS7. Replies of a message
\set messageId 618475290624
 */
SELECT
    p2.MessageId,
    p2.content,
    p2.creationDate,
    Person.personId,
    Person.firstName,
    Person.lastName,
    (
        CASE WHEN EXISTS (
            SELECT 1
            FROM Person_knows_Person
            WHERE p1.CreatorPersonId = Person1Id
              AND p2.CreatorPersonId = Person2Id
        )
        THEN 1
        ELSE 0
        END
    )
FROM Message p1, Message p2, Person
WHERE p1.MessageId = :messageId
  AND p2.ParentMessageId = p1.MessageId
  AND p2.CreatorPersonId = Person.personId
ORDER BY p2.creationDate DESC, p2.CreatorPersonId ASC
;