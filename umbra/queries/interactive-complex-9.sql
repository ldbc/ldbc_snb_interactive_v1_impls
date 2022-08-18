/* Q9. Recent messages by friends or friends of friends
\set PersonId 17592186044461
\set maxDate '\'2010-11-16\''::date
 */
SELECT
    Person.id,
    firstName,
    lastName,
    Message.id,
    coalesce(imageFile, content),
    Message.creationDate
FROM
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
    ) friend,
    Person,
    Message
WHERE Person.id = CreatorPersonId
  AND Person.id = friend.Person2Id
  AND Message.creationDate < :maxDate
ORDER BY Message.creationDate DESC, Message.id ASC
LIMIT 20
;
