MATCH
  (person:Person {id: $personId})-[:KNOWS*1..2]-(friend:Person)<-[:HAS_CREATOR]-(messageX:Message),
  (messageX)-[:IS_LOCATED_IN]->(countryX:Country {name: $countryXName})
WHERE person <> friend
  AND NOT (friend)-[:IS_LOCATED_IN]->()-[:IS_PART_OF]->(countryX)
  AND messageX.creationDate >= datetime($startDate)
  AND messageX.creationDate < datetime($startDate) + duration({days: $durationDays})
WITH friend, count(DISTINCT messageX) AS xCount
MATCH (friend)<-[:HAS_CREATOR]-(messageY:Message)-[:IS_LOCATED_IN]->(countryY:Country {name: $countryYName})
WHERE NOT (friend)-[:IS_LOCATED_IN]->()-[:IS_PART_OF]->(countryY)
  AND messageY.creationDate >= datetime($startDate)
  AND messageY.creationDate < datetime($startDate) + duration({days: $durationDays})
WITH
  friend.id AS personId,
  friend.firstName AS personFirstName,
  friend.lastName AS personLastName,
  xCount,
  count(DISTINCT messageY) AS yCount
RETURN
  personId,
  personFirstName,
  personLastName,
  xCount,
  yCount,
  xCount + yCount AS count
ORDER BY count DESC, personId ASC
LIMIT 20
