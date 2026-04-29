SET search_path = ag_catalog, public;
SELECT tagName, SUM(postCount::text::bigint)::bigint AS postCount FROM (
  SELECT * FROM cypher('$graphName', $$
    MATCH (p:Person {id: $personId})-[:KNOWS]->(friend:Person)<-[:HAS_CREATOR]-(post:Post)-[:HAS_TAG]->(tag:Tag)
    WHERE tag.name <> $tagName
    MATCH (friend)<-[:HAS_CREATOR]-(:Post)-[:HAS_TAG]->(:Tag {name: $tagName})
    WITH tag.name AS tagName, count(DISTINCT post) AS postCount
    RETURN tagName, postCount
    ORDER BY postCount DESC, tagName ASC
  $$) AS (tagName agtype, postCount agtype)
  UNION ALL
  SELECT * FROM cypher('$graphName', $$
    MATCH (p:Person {id: $personId})-[:KNOWS]->(:Person)-[:KNOWS]->(friend:Person)<-[:HAS_CREATOR]-(post:Post)-[:HAS_TAG]->(tag:Tag)
    WHERE friend.id <> $personId AND tag.name <> $tagName
    MATCH (friend)<-[:HAS_CREATOR]-(:Post)-[:HAS_TAG]->(:Tag {name: $tagName})
    WITH tag.name AS tagName, count(DISTINCT post) AS postCount
    RETURN tagName, postCount
    ORDER BY postCount DESC, tagName ASC
  $$) AS (tagName agtype, postCount agtype)
) tags
GROUP BY tagName
ORDER BY SUM(postCount::text::bigint) DESC, tagName ASC
LIMIT 10;
