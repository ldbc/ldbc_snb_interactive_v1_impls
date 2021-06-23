// Q9. Recent messages by friends or friends of friends
/*
:param [{ personId, maxDate }] => { RETURN
  4398046511268 AS personId,
  datetime('2010-11-16') AS maxDate
}
*/
MATCH (:Person {id: $personId})-[:KNOWS*1..2]-(otherPerson:Person)<-[:HAS_CREATOR]-(message:Message)
WHERE message.creationDate < datetime($maxDate)
RETURN DISTINCT
  otherPerson.id AS otherPersonId,
  otherPerson.firstName AS otherPersonFirstName,
  otherPerson.lastName AS otherPersonLastName,
  message.id AS messageId,
  coalesce(message.content, message.imageFile) AS messageContent,
  message.creationDate AS messageCreationDate
ORDER BY message.creationDate DESC, message.id ASC
LIMIT 20
