// Q19. Stranger's interaction
/*
  :param {
    date: 19890101,
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
  (:TagClass {name: $tagClass1})<-[:HAS_TYPE]-(:Tag)<-[:HAS_TAG]-(forum1:Forum),
  (:TagClass {name: $tagClass2})<-[:HAS_TYPE]-(:Tag)<-[:HAS_TAG]-(forum2:Forum),
  (forum1)-[:HAS_MEMBER]->(stranger:Person)<-[:hasMember]-(forum2)
WHERE person <> stranger
  AND NOT (person)-[:KNOWS]-(stranger)
WITH person, stranger
OPTIONAL MATCH
    (person)<-[:HAS_CREATOR]-(comment1:Comment)-[:REPLY_OF]->(:Message)-[:HAS_CREATOR]->(stranger)
OPTIONAL MATCH
  (stranger)<-[:HAS_CREATOR]-(comment2:Comment)-[:REPLY_OF]->(:Message)-[:HAS_CREATOR]->(person)
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
