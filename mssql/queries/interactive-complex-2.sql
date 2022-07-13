SELECT TOP(20)
    Person.personId,
    Person.firstName,
    Person.lastName,
    Message.MessageId,
    coalesce(Message.imageFile, Message.content),
    Message.creationDate
FROM
    Person,
    Message,
    Person_knows_Person
WHERE Person.personId = Message.CreatorPersonId
  AND Message.creationDate <= :maxDate
  AND Person1Id = :personId
  AND Person2Id = Person.personId
ORDER BY creationDate DESC, MessageId ASC
;
