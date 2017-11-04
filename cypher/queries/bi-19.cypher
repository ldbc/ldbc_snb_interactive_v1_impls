// Q19. Stranger's interaction
/*
  :param {
    date: '1989-01-01',
    tagClass1: 'MusicalArtist',
    tagClass2: 'OfficeHolder'
  }
*/
MATCH (person:Person)
WHERE person.birthday > $date
MATCH
// The tags may be attached to the same Forum
// or they may not be attached to different Forums.
// --> may be use two MATCH clauses?
  (:TagClass {name: $tagClass1})<-[:hasType]-(:Tag)<-[:hasTag]-(forum1:Forum),
  (:TagClass {name: $tagClass2})<-[:hasType]-(:Tag)<-[:hasTag]-(forum2:Forum),
  (forum1)-[:hasMember]->(stranger:Person)<-[:hasMember]-(forum2),
  (person)-[:knows]-(stranger)
WHERE NOT (person)-[:knows]-(stranger)
WITH person, stranger
OPTIONAL MATCH
  (person)  <-[:hasCreator]-(comment1:Comment)-[:replyOf]->(:Message)-[:hasCreator]->(stranger),
  (stranger)<-[:hasCreator]-(comment2:Comment)-[:replyOf]->(:Message)-[:hasCreator]->(person)
WITH
  person,
  count(stranger) AS strangersCount,
  count(comment1) AS comment1Count,
  count(comment2) AS comment2Count
RETURN
  person.id,
  strangersCount,
  comment1Count + comment2Count AS interactionCount
  ORDER BY
  interactionCount DESC,
  person.id ASC
  LIMIT 100
