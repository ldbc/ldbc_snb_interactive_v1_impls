SELECT TOP(20)
    Person.personId,
    firstName,
    lastName,
    MessageId,
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
WHERE Person.personId = CreatorPersonId
  AND Person.personId = friend.Person2Id
  AND Message.creationDate < :maxDate
ORDER BY Message.creationDate DESC, MessageId ASC
;
