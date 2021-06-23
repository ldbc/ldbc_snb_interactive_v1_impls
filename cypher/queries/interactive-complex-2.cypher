// Q2. Recent messages by your friends
/*
:param [{ personId, maxDate }] => { RETURN
  10995116278009 AS personId,
  '2010-10-16' AS maxDate
}
 */
MATCH (:Person {id: $personId})-[:KNOWS]-(friend:Person)<-[:HAS_CREATOR]-(message:Message)
WHERE message.creationDate < datetime($maxDate)
RETURN
  friend.id AS personId,
  friend.firstName AS personFirstName,
  friend.lastName AS personLastName,
  message.id AS messageId,
  coalesce(message.content, message.imageFile) AS messageContent,
  message.creationDate AS messageCreationDate
ORDER BY messageCreationDate DESC, messageId ASC
LIMIT 20
