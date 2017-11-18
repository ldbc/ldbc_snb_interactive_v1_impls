// Q25. Weighted paths
/*
  :param {
    person1Id: 2199023264119,
    person2Id: 8796093028894,
    startDate: 20100601040000000,
    endDate: 20100701040000000
  }
*/
MATCH path=(p1:Person {id: $person1Id})-[:KNOWS*]-(p2:Person {id: $person2Id})
WITH p1, p2, path
ORDER BY length(path)
WITH p1, p2, collect(path)[0] AS path // select the shortest path
UNWIND relationships(path) AS k
WITH
  path,
  startNode(k) AS pA,
  endNode(k) AS pB,
  0 AS weight
// A to B
// every reply (by one of the Persons) to a Post (by the other Person): 1.0
OPTIONAL MATCH
  (pA)<-[:HAS_CREATOR]-(c:Comment)-[:REPLY_OF]->(m:Post)-[:HAS_CREATOR]->(pB),
  (m)<-[:CONTAINER_OF]-(forum:Forum)
WHERE forum.creationDate >= $startDate AND forum.creationDate <= $endDate
WITH path, pA, pB, weight + count(c)*1.0 AS weight

// A to B
// every reply (by ones of the Persons) to a Comment (by the other Person): 0.5
OPTIONAL MATCH
  (pA)<-[:HAS_CREATOR]-(c:Comment)-[:REPLY_OF]->(m:Comment)-[:HAS_CREATOR]->(pB),
  (m)<-[:CONTAINER_OF]-(forum:Forum)
WHERE forum.creationDate >= $startDate AND forum.creationDate <= $endDate
WITH path, pA, pB, weight + count(c)*0.5 AS weight

// B to A
// every reply (by one of the Persons) to a Post (by the other Person): 1.0
OPTIONAL MATCH
  (pB)<-[:HAS_CREATOR]-(c:Comment)-[:REPLY_OF]->(m:Post)-[:HAS_CREATOR]->(pA),
  (m)<-[:CONTAINER_OF]-(forum:Forum)
WHERE forum.creationDate >= $startDate AND forum.creationDate <= $endDate
WITH path, pA, pB, weight + count(c)*1.0 AS weight

// B to A
// every reply (by ones of the Persons) to a Comment (by the other Person): 0.5
OPTIONAL MATCH
  (pB)<-[:HAS_CREATOR]-(c:Comment)-[:REPLY_OF]->(m:Comment)-[:HAS_CREATOR]->(pA),
  (m)<-[:CONTAINER_OF]-(forum:Forum)
WHERE forum.creationDate >= $startDate AND forum.creationDate <= $endDate
WITH path, pA, pB, weight + count(c)*0.5 AS weight

RETURN
  [person IN nodes(path) | person.id]
ORDER BY
  weight DESC
