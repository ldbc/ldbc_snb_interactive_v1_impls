SET search_path = ag_catalog, public;
SELECT * FROM cypher('$graphName', $$
  MATCH (mod:Person {id: $moderatorPersonId})
  CREATE (f:Forum {id: $forumId, title: $forumTitle, creationDate: $creationDate})-[:HAS_MODERATOR]->(mod)
  WITH f
  UNWIND $tagIds AS tagId
    MATCH (t:Tag {id: tagId})
    CREATE (f)-[:HAS_TAG]->(t)
  RETURN count(f)
$$) AS (result agtype);
