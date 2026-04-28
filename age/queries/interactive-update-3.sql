SET search_path = ag_catalog, public;
SELECT * FROM cypher('$graphName', $$
  MATCH (person:Person {id: $personId}), (comment:Comment {id: $commentId})
  CREATE (person)-[:LIKES {creationDate: $creationDate}]->(comment)
  RETURN count(*)
$$) AS (result agtype);
