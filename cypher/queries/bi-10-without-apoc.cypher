// Q10. Experts in social circle
/*
  :param [{personId, country, tagClass}] => { RETURN 17592186054360 AS personId, 'Pakistan' AS country, 'MusicalArtist' AS tagClass }

*/
MATCH (startPerson:Person {id: $personId})
MATCH p=shortestPath((startPerson)-[:KNOWS*..2]-(expertCandidatePerson:Person))
WHERE startPerson <> expertCandidatePerson
WITH startPerson, collect(DISTINCT expertCandidatePerson) AS nodesCloserThanMinPathDistance
MATCH p=shortestPath((startPerson)-[:KNOWS*..5]-(expertCandidatePerson:Person))
WHERE startPerson <> expertCandidatePerson
WITH nodesCloserThanMinPathDistance, collect(DISTINCT expertCandidatePerson) AS nodesCloserThanMaxPathDistance
WITH [n IN nodesCloserThanMaxPathDistance WHERE NOT n IN nodesCloserThanMinPathDistance] AS expertCandidatePersons
UNWIND expertCandidatePersons AS expertCandidatePerson
MATCH
  (expertCandidatePerson)-[:IS_LOCATED_IN]->(:City)-[:IS_PART_OF]->(:Country {name: $country}),
  (expertCandidatePerson)<-[:HAS_CREATOR]-(message:Message)-[:HAS_TAG]->(:Tag)-[:HAS_TYPE]->
  (:TagClass {name: $tagClass})
MATCH
  (message)-[:HAS_TAG]->(tag:Tag)
RETURN
  expertCandidatePerson.id,
  tag.name,
  count(DISTINCT message) AS messageCount
ORDER BY
  messageCount DESC,
  tag.name ASC,
  expertCandidatePerson.id ASC
LIMIT 100
