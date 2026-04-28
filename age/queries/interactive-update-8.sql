SET search_path = ag_catalog, public;
SELECT * FROM cypher('$graphName', $$
  MATCH (p1:Person {id: $person1Id}), (p2:Person {id: $person2Id})
  CREATE (p1)-[:KNOWS {creationDate: $creationDate}]->(p2),
         (p2)-[:KNOWS {creationDate: $creationDate}]->(p1)
  RETURN count(*)
$$) AS (result agtype);
