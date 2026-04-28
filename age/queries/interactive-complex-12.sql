SET search_path = ag_catalog, public;
SELECT personId, personFirstName, personLastName, tagNames, replyCount
FROM (
  SELECT * FROM cypher('$graphName', $$
    MATCH (base:TagClass {name: $tagClassName})<-[:HAS_TYPE]-(tag:Tag)<-[:HAS_TAG]-(reply:Comment)-[:REPLY_OF]->(post:Post),
          (reply)-[:HAS_CREATOR]->(friend:Person),
          (p:Person {id: $personId})-[:KNOWS]->(friend)
    WITH friend, collect(DISTINCT tag.name) AS tagNames, count(DISTINCT reply) AS replyCount
    RETURN friend.id, friend.firstName, friend.lastName, tagNames, replyCount
    ORDER BY replyCount DESC, toInteger(friend.id) ASC
  $$) AS (personId agtype, personFirstName agtype, personLastName agtype,
          tagNames agtype, replyCount agtype)
  UNION ALL
  SELECT * FROM cypher('$graphName', $$
    MATCH (base:TagClass {name: $tagClassName})<-[:IS_SUBCLASS_OF*1..5]-(sub:TagClass)<-[:HAS_TYPE]-(tag:Tag)<-[:HAS_TAG]-(reply:Comment)-[:REPLY_OF]->(post:Post),
          (reply)-[:HAS_CREATOR]->(friend:Person),
          (p:Person {id: $personId})-[:KNOWS]->(friend)
    WITH friend, collect(DISTINCT tag.name) AS tagNames, count(DISTINCT reply) AS replyCount
    RETURN friend.id, friend.firstName, friend.lastName, tagNames, replyCount
    ORDER BY replyCount DESC, toInteger(friend.id) ASC
  $$) AS (personId agtype, personFirstName agtype, personLastName agtype,
          tagNames agtype, replyCount agtype)
) all_experts
GROUP BY personId, personFirstName, personLastName, tagNames
ORDER BY replyCount DESC, personId ASC
LIMIT 20;
