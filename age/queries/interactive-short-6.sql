SET search_path = ag_catalog, public;
SELECT * FROM (
  SELECT * FROM cypher('$graphName', $$
    MATCH (m:Comment {id: $messageId})-[:REPLY_OF*0..]->(post:Post)<-[:CONTAINER_OF]-(forum:Forum)-[:HAS_MODERATOR]->(mod:Person)
    RETURN forum.id, forum.title, mod.id, mod.firstName, mod.lastName
  $$) AS (forumId agtype, forumTitle agtype, moderatorId agtype,
          moderatorFirstName agtype, moderatorLastName agtype)
  UNION ALL
  SELECT * FROM cypher('$graphName', $$
    MATCH (post:Post {id: $messageId})<-[:CONTAINER_OF]-(forum:Forum)-[:HAS_MODERATOR]->(mod:Person)
    RETURN forum.id, forum.title, mod.id, mod.firstName, mod.lastName
  $$) AS (forumId agtype, forumTitle agtype, moderatorId agtype,
          moderatorFirstName agtype, moderatorLastName agtype)
) forum
LIMIT 1;
