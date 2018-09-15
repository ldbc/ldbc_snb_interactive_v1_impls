MATCH (:Person {id:$personId})-[:KNOWS*1..2]-(friend:Person)<-[:HAS_CREATOR]-(message)
WHERE message.creationDate < $maxDate
RETURN DISTINCT
  friend.id AS personId,
  friend.firstName AS personFirstName,
  friend.lastName AS personLastName,
  message.id AS commentOrPostId,
  CASE exists(message.content)
    WHEN true THEN message.content
    ELSE message.imageFile
  END AS commentOrPostContent,
  message.creationDate AS commentOrPostCreationDate
ORDER BY message.creationDate DESC, toInteger(message.id) ASC
LIMIT 20

