SET search_path = ag_catalog, public;
SELECT * FROM cypher('$graphName', $$
  MATCH (p:Person {id: $personId})-[:KNOWS]->(friend:Person)
  MATCH (friend)<-[:HAS_CREATOR]-(reply:Comment)-[:REPLY_OF]->(:Post)
  MATCH (reply)-[:HAS_TAG]->(tag:Tag)-[:HAS_TYPE]->(tc:TagClass)-[:IS_SUBCLASS_OF*0..]->(base:TagClass {name: $tagClassName})
  WITH friend, collect(DISTINCT tag.name) AS tagNames, count(DISTINCT reply) AS replyCount
  RETURN friend.id, friend.firstName, friend.lastName, tagNames, replyCount
  ORDER BY replyCount DESC, toInteger(friend.id) ASC
$$) AS (personId agtype, personFirstName agtype, personLastName agtype,
        tagNames agtype, replyCount agtype)
LIMIT 20;
