SET search_path = ag_catalog, public;
SELECT * FROM cypher('$graphName', $$
  MATCH (forum:Forum {id: $forumId}), (person:Person {id: $personId})
  CREATE (forum)-[:HAS_MEMBER {joinDate: $joinDate}]->(person)
  RETURN count(*)
$$) AS (result agtype);
