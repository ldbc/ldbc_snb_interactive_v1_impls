/* Q2. Recent messages by your friends
\set personId 17592186044461
\set maxDate '\'2010-10-16\''
 */
SELECT
    Person.id,
    Person.firstName,
    Person.lastName,
    Message.id,
    coalesce(Message.imageFile, Message.content),
    Message.creationDate
FROM
    Person,
    Message,
    Person_knows_Person
WHERE Person.id = Message.CreatorPersonId
  AND Message.creationDate < :maxDate
  AND Person1Id = :personId
  AND Person2Id = Person.id
ORDER BY creationDate DESC, Message.id ASC
LIMIT 20
;
