SET search_path = ag_catalog, public;
SELECT * FROM cypher('$graphName', $$
  MATCH (n:Person {id: $personId})-[r:KNOWS]-(friend:Person)
  RETURN friend.id, friend.firstName, friend.lastName, r.creationDate
  ORDER BY r.creationDate DESC, toInteger(friend.id) ASC
$$) AS (personId agtype, firstName agtype, lastName agtype, friendshipCreationDate agtype);
