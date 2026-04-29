SET search_path = ag_catalog, public;
SELECT friendId, friendFirstName, friendLastName,
       SUM(CASE WHEN countryName::text = $countryXName THEN 1 ELSE 0 END)::int AS xCount,
       SUM(CASE WHEN countryName::text = $countryYName THEN 1 ELSE 0 END)::int AS yCount,
       COUNT(*)::int AS xyCount
FROM (
  SELECT * FROM cypher('$graphName', $$
    MATCH (p:Person {id: $personId})-[:KNOWS]->(friend:Person)<-[:HAS_CREATOR]-(msg:Comment)-[:IS_LOCATED_IN]->(country:Country),
          (friend)-[:IS_LOCATED_IN]->(fCity:City)-[:IS_PART_OF]->(fCountry:Country)
    WHERE msg.creationDate >= $startDate AND msg.creationDate < $endDate
      AND country.name IN [$countryXName, $countryYName]
      AND fCountry.name <> $countryXName AND fCountry.name <> $countryYName
    RETURN friend.id, friend.firstName, friend.lastName, country.name
  $$) AS (friendId agtype, friendFirstName agtype, friendLastName agtype, countryName agtype)
  UNION ALL
  SELECT * FROM cypher('$graphName', $$
    MATCH (p:Person {id: $personId})-[:KNOWS]->(friend:Person)<-[:HAS_CREATOR]-(msg:Post)-[:IS_LOCATED_IN]->(country:Country),
          (friend)-[:IS_LOCATED_IN]->(fCity:City)-[:IS_PART_OF]->(fCountry:Country)
    WHERE msg.creationDate >= $startDate AND msg.creationDate < $endDate
      AND country.name IN [$countryXName, $countryYName]
      AND fCountry.name <> $countryXName AND fCountry.name <> $countryYName
    RETURN friend.id, friend.firstName, friend.lastName, country.name
  $$) AS (friendId agtype, friendFirstName agtype, friendLastName agtype, countryName agtype)
  UNION ALL
  SELECT * FROM cypher('$graphName', $$
    MATCH (p:Person {id: $personId})-[:KNOWS]->(:Person)-[:KNOWS]->(friend:Person)
    WHERE friend.id <> $personId
    OPTIONAL MATCH (p)-[direct:KNOWS]->(friend)
    WITH DISTINCT friend, direct WHERE direct IS NULL
    MATCH (friend)<-[:HAS_CREATOR]-(msg:Comment)-[:IS_LOCATED_IN]->(country:Country),
          (friend)-[:IS_LOCATED_IN]->(fCity:City)-[:IS_PART_OF]->(fCountry:Country)
    WHERE msg.creationDate >= $startDate AND msg.creationDate < $endDate
      AND country.name IN [$countryXName, $countryYName]
      AND fCountry.name <> $countryXName AND fCountry.name <> $countryYName
    RETURN friend.id, friend.firstName, friend.lastName, country.name
  $$) AS (friendId agtype, friendFirstName agtype, friendLastName agtype, countryName agtype)
  UNION ALL
  SELECT * FROM cypher('$graphName', $$
    MATCH (p:Person {id: $personId})-[:KNOWS]->(:Person)-[:KNOWS]->(friend:Person)
    WHERE friend.id <> $personId
    OPTIONAL MATCH (p)-[direct:KNOWS]->(friend)
    WITH DISTINCT friend, direct WHERE direct IS NULL
    MATCH (friend)<-[:HAS_CREATOR]-(msg:Post)-[:IS_LOCATED_IN]->(country:Country),
          (friend)-[:IS_LOCATED_IN]->(fCity:City)-[:IS_PART_OF]->(fCountry:Country)
    WHERE msg.creationDate >= $startDate AND msg.creationDate < $endDate
      AND country.name IN [$countryXName, $countryYName]
      AND fCountry.name <> $countryXName AND fCountry.name <> $countryYName
    RETURN friend.id, friend.firstName, friend.lastName, country.name
  $$) AS (friendId agtype, friendFirstName agtype, friendLastName agtype, countryName agtype)
) msgs
GROUP BY friendId, friendFirstName, friendLastName
HAVING SUM(CASE WHEN countryName::text = $countryXName THEN 1 ELSE 0 END) > 0
   AND SUM(CASE WHEN countryName::text = $countryYName THEN 1 ELSE 0 END) > 0
ORDER BY xyCount DESC, friendId ASC
LIMIT 20;
