SELECT TOP(20)
    Person.id,
    Person.firstName,
    Person.lastName,
    Message.MessageId,
    coalesce(Message.imageFile, Message.content),
    Message.creationDate
FROM
    Person,
    Message,
    Person_knows_Person
WHERE Person.id = Message.CreatorPersonId
  AND Message.creationDate <= :maxDate
  AND Person1Id = :personId
  AND Person2Id = Person.id
ORDER BY creationDate DESC, MessageId ASC
;
