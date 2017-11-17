// Q9. Forum with related Tags
/*
  :param {
    tagClass1: 'TennisPlayer',
    tagClass2: 'President',
    threshold: 200
  }
*/
MATCH
  (forum:Forum),
  (forum)-[:CONTAINER_OF]->(post1:Post)-[:HAS_TAG]->(:Tag)-[:HAS_TYPE]->(:TagClass {name: $tagClass1}),
  (forum)-[:CONTAINER_OF]->(post2:Post)-[:HAS_TAG]->(:Tag)-[:HAS_TYPE]->(:TagClass {name: $tagClass2}),
  (forum)-[:HAS_MEMBER]->(person:Person)
WITH forum, count(post1) AS count1, count(post2) AS count2, count(person) AS members
WHERE members > 0
RETURN
  forum.id,
  count1,
  count2
ORDER BY
  abs(count2-count1) DESC,
  forum.id ASC
LIMIT 100
