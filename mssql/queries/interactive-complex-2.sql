SELECT TOP(20)
    p2.personId,
    p2.firstName,
    p2.lastName,
    Message.MessageId,
    coalesce(Message.imageFile, Message.content),
    Message.creationDate
FROM 
    Person AS p1,
    Person_knows_Person,
    Person AS p2,
    Message
WHERE MATCH(p1-(Person_knows_Person)->p2)
    AND p2.personId = Message.CreatorPersonId
    AND Message.creationDate < :maxDate
    AND p1.personId = :personId
ORDER BY creationDate DESC, MessageId ASC