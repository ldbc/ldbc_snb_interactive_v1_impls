SET search_path = ag_catalog, public;
SELECT * FROM cypher('$graphName', $$
  MATCH (person:Person {id: $personId}), (post:Post {id: $postId})
  CREATE (person)-[:LIKES {creationDate: $creationDate}]->(post)
  RETURN count(*)
$$) AS (result agtype);
