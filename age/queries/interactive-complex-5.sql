SET search_path = ag_catalog, public;
SELECT forumTitle, SUM(postCount)::int AS postCount
FROM (
  SELECT DISTINCT ON (friendId, forumId) friendId, forumId, forumTitle, postCount
  FROM (
    SELECT * FROM cypher('$graphName', $$
      MATCH (p:Person {id: $personId})-[:KNOWS]->(friend:Person)<-[member:HAS_MEMBER]-(forum:Forum)
      WHERE member.joinDate > $minDate AND friend.id <> $personId
      OPTIONAL MATCH (friend)<-[:HAS_CREATOR]-(post:Post)<-[:CONTAINER_OF]-(forum)
      RETURN friend.id, forum.id, forum.title, count(post)
    $$) AS (friendId agtype, forumId agtype, forumTitle agtype, postCount agtype)
    UNION ALL
    SELECT * FROM cypher('$graphName', $$
      MATCH (p:Person {id: $personId})-[:KNOWS]->(:Person)-[:KNOWS]->(friend:Person)<-[member:HAS_MEMBER]-(forum:Forum)
      WHERE member.joinDate > $minDate AND friend.id <> $personId
      OPTIONAL MATCH (friend)<-[:HAS_CREATOR]-(post:Post)<-[:CONTAINER_OF]-(forum)
      RETURN friend.id, forum.id, forum.title, count(post)
    $$) AS (friendId agtype, forumId agtype, forumTitle agtype, postCount agtype)
  ) all_pairs
  ORDER BY friendId, forumId
) deduped
GROUP BY forumId, forumTitle
ORDER BY postCount DESC, forumId ASC
LIMIT 20;
