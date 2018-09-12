MATCH (m:Message {id:{id}})-[:HAS_CREATOR]->(p:Person)
RETURN
  p.id AS personId,
  p.firstName AS firstName,
  p.lastName AS lastName;
