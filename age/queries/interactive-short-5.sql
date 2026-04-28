SET search_path = ag_catalog, public;
SELECT * FROM (
  SELECT * FROM cypher('$graphName', $$
    MATCH (m:Comment {id: $messageId})-[:HAS_CREATOR]->(p:Person)
    RETURN p.id, p.firstName, p.lastName
  $$) AS (personId agtype, firstName agtype, lastName agtype)
  UNION ALL
  SELECT * FROM cypher('$graphName', $$
    MATCH (m:Post {id: $messageId})-[:HAS_CREATOR]->(p:Person)
    RETURN p.id, p.firstName, p.lastName
  $$) AS (personId agtype, firstName agtype, lastName agtype)
) creator
LIMIT 1;
