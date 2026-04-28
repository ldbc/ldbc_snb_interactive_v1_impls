SET search_path = ag_catalog, public;
SELECT tagName, postCount FROM (
  SELECT * FROM cypher('$graphName', $$
    MATCH (p:Person {id: $personId})-[:KNOWS]->(friend:Person)<-[:HAS_CREATOR]-(post:Post)-[:HAS_TAG]->(tag:Tag)
    WHERE post.creationDate >= $startDate AND post.creationDate < $endDate
    WITH tag, count(post) AS postCount
    WHERE NOT EXISTS {
      MATCH (:Person {id: $personId})-[:KNOWS]->(:Person)<-[:HAS_CREATOR]-(oldPost:Post)-[:HAS_TAG]->(tag)
      WHERE oldPost.creationDate < $startDate
    }
    RETURN tag.name, postCount
    ORDER BY postCount DESC, tag.name ASC
  $$) AS (tagName agtype, postCount agtype)
) tags
ORDER BY postCount DESC, tagName ASC
LIMIT 10;
