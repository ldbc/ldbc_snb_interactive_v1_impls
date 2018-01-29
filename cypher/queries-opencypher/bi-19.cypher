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
  (person:Person)<-[:HAS_CREATOR]-(:Message)-[:REPLY_OF]-
  (:Message)-[:HAS_CREATOR]->(stranger)
WHERE person.birthday > $date
  AND person <> stranger
  AND NOT (person)-[:KNOWS]-(stranger)
WITH person, stranger
OPTIONAL MATCH
  (person)<-[:HAS_CREATOR]-(comment1:Comment)-[:REPLY_OF]->(:Message)-[:HAS_CREATOR]->(stranger)
OPTIONAL MATCH
  (stranger)<-[:HAS_CREATOR]-(comment2:Comment)-[:REPLY_OF]->(:Message)-[:HAS_CREATOR]->(person)
WITH
  person,
  count(DISTINCT stranger) AS strangersCount,
  count(DISTINCT comment1) AS comment1Count,
  count(DISTINCT comment2) AS comment2Count
RETURN
  person.id,
  strangersCount,
  comment1Count + comment2Count AS interactionCount
ORDER BY
  interactionCount DESC,
  person.id ASC
LIMIT 100
