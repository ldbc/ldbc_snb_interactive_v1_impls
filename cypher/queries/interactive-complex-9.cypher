MATCH (:Person {id: $personId})-[:KNOWS*1..2]-(friend:Person)<-[:HAS_CREATOR]-(message:Message)
WHERE message.creationDate < datetime($maxDate)
RETURN DISTINCT
  friend.id AS personId,
  friend.firstName AS personFirstName,
  friend.lastName AS personLastName,
  message.id AS messageId,
  coalesce(message.content, message.imageFile) AS messageContent,
  message.creationDate AS messageCreationDate
ORDER BY message.creationDate DESC, message.id ASC
LIMIT 20

