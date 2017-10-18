// Q19. Stranger's interaction
// :param date
// :param tagClass1
// :param tagClass2
MATCH
  (:TagClass)<-[:hasType]-(:Tag)<-[:hasTag]-(forum1:Forum),
  (:TagClass)<-[:hasType]-(:Tag)<-[:hasTag]-(forum2:Forum),
  (forum1)-[:hasMember]->(person:Person)<-[:hasMember]-(forum2),
  (forum1)-[:hasMember]->(stranger:Person)<-[:hasMember]-(forum2)
WHERE NOT (person)-[:knows]-(stranger)
  AND person.birthday > '1950-01-01T00:00:00.000+0000'
WITH person, stranger
MATCH (person)<-[:hasCreator]-(:Message)-[:replyOf]-(comment1:Comment)-[:hasCreator]->(stranger),
  (stranger)<-[:hasCreator]-(:Message)<-[:replyOf]-(comment2:Comment)-[:hasCreator]->(person)
WITH person, count(stranger) AS strangersCount, count(comment1) AS comment1Count, count(comment2) AS comment2Count
RETURN
  person.id,
  strangersCount,
  comment1Count + comment2Count AS interactionCount
ORDER BY
  interactionCount DESC,
  person.id ASC
LIMIT 100
