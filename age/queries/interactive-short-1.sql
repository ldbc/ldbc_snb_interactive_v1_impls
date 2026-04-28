SET search_path = ag_catalog, public;
SELECT * FROM cypher('$graphName', $$
  MATCH (n:Person {id: $personId})-[:IS_LOCATED_IN]->(city:City)
  RETURN n.firstName, n.lastName, n.birthday, n.locationIP, n.browserUsed, city.id, n.gender, n.creationDate
$$) AS (firstName agtype, lastName agtype, birthday agtype, locationIP agtype,
        browserUsed agtype, cityId agtype, gender agtype, creationDate agtype);
