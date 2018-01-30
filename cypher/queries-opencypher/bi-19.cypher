// Q19. Stranger's interaction
/*
  :param {
    date: 19890101,
    tagClass1: 'MusicalArtist',
    tagClass2: 'OfficeHolder'
  }
*/
MATCH
  (:TagClass {name: $tagClass1})<-[:HAS_TYPE]-(:Tag)<-[:HAS_TAG]-
  (forum1:Forum)-[:HAS_MEMBER]->(stranger:Person)
WITH DISTINCT stranger
MATCH
  (:TagClass {name: $tagClass2})<-[:HAS_TYPE]-(:Tag)<-[:HAS_TAG]-
  (forum2:Forum)-[:HAS_MEMBER]->(stranger)
WITH DISTINCT stranger
MATCH
  (person)<-[:HAS_CREATOR]-(comment:Comment)-[:REPLY_OF]->(:Message)-[:HAS_CREATOR]->(stranger)
WHERE person.birthday > $date
  AND person <> stranger
  AND NOT (person)-[:KNOWS]-(stranger)
RETURN
  person.id,
  count(DISTINCT stranger) AS strangersCount,
  count(comment) AS interactionCount
ORDER BY
  interactionCount DESC,
  person.id ASC
LIMIT 100
