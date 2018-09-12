MATCH (n:Person {id:{id}})-[r:KNOWS]-(friend)
RETURN
  friend.id AS personId,
  friend.firstName AS firstName,
  friend.lastName AS lastName,
  r.creationDate AS friendshipCreationDate
ORDER BY friendshipCreationDate DESC, toInt(personId) ASC;
