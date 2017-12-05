// Q9. Forum with related Tags
/*
  :param {
    tagClass1: 'BaseballPlayer',
    tagClass2: 'ChristianBishop',
    threshold: 200
  }
*/
MATCH
  (forum:Forum)-[:HAS_MEMBER]->(person:Person)
WITH forum, count(person) AS members
WHERE members > $threshold
MATCH
  (forum)-[:CONTAINER_OF]->(post1:Post)-[:HAS_TAG]->
  (:Tag)-[:HAS_TYPE]->(:TagClass {name: $tagClass1})
WITH forum, count(DISTINCT post1) AS count1
MATCH
  (forum)-[:CONTAINER_OF]->(post2:Post)-[:HAS_TAG]->
  (:Tag)-[:HAS_TYPE]->(:TagClass {name: $tagClass2})
WITH forum, count1, count(DISTINCT post2) AS count2
RETURN
  forum.id,
  count1,
  count2
ORDER BY
  abs(count2-count1) DESC,
  forum.id ASC
LIMIT 100
