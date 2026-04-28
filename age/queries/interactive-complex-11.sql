SET search_path = ag_catalog, public;
SELECT * FROM (
  SELECT * FROM cypher('$graphName', $$
    MATCH (p:Person {id: $personId})-[:KNOWS]->(friend:Person)-[work:WORK_AT]->(company:Company)
    WHERE company.placeName = $countryName AND toInteger(work.workFrom) < $workFromYear
      AND friend.id <> $personId
    RETURN friend.id, friend.firstName, friend.lastName, company.name, toInteger(work.workFrom)
  $$) AS (personId agtype, personFirstName agtype, personLastName agtype,
          organizationName agtype, organizationWorkFromYear agtype)
  UNION ALL
  SELECT * FROM cypher('$graphName', $$
    MATCH (p:Person {id: $personId})-[:KNOWS]->(:Person)-[:KNOWS]->(friend:Person)-[work:WORK_AT]->(company:Company)
    WHERE company.placeName = $countryName AND toInteger(work.workFrom) < $workFromYear
      AND friend.id <> $personId
    RETURN friend.id, friend.firstName, friend.lastName, company.name, toInteger(work.workFrom)
  $$) AS (personId agtype, personFirstName agtype, personLastName agtype,
          organizationName agtype, organizationWorkFromYear agtype)
) results
ORDER BY organizationWorkFromYear ASC, personId ASC, organizationName DESC
LIMIT 10;
