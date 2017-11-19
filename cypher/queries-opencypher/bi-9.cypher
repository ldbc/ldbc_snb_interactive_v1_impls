// Q9. Forum with related Tags
/*
  :param {
    tagClass1: 'BaseballPlayer',
    tagClass2: 'ChristianBishop',
    threshold: 200
  }
*/
MATCH
  (forum:Forum)-[:CONTAINER_OF]->(post1:Post)-[:HAS_TAG]->
  (:Tag)-[:HAS_TYPE]->(:TagClass {name: $tagClass1})
WITH forum, count(post1) AS count1,  collect(post1) AS post1s
MATCH
  (forum)-[:CONTAINER_OF]->(post2:Post)-[:HAS_TAG]->
  (:Tag)-[:HAS_TYPE]->(:TagClass {name: $tagClass2})
WITH forum, count1, count(post2) AS count2
MATCH
  (forum)-[:HAS_MEMBER]->(person:Person)
WITH forum, count1, count2, count(person) AS members
WHERE members > $threshold
RETURN
  forum.id,
  count1,
  count2
ORDER BY
  abs(count2-count1) DESC,
  forum.id ASC
LIMIT 100
