// Q19. Interaction path between cities
// Requires the Neo4j Graph Data Science Library
/*
  :param [{city1Id, city2Id}] => {RETURN 1178 AS city1Id, 1142 AS city2Id}
*/
MATCH
  (person1:Person)-[:IS_LOCATED_IN]->(city1:City {id: $city1Id}),
  (person2:Person)-[:IS_LOCATED_IN]->(city2:City {id: $city2Id})
CALL gds.alpha.shortestPath.stream({
  nodeQuery: 'MATCH (p:Person) RETURN id(p) AS id',
  relationshipQuery:
    'MATCH
       (personA:Person)-[:KNOWS]-(personB:Person),
       (personA)<-[:HAS_CREATOR]-(:Message)-[replyOf:REPLY_OF]-(:Message)-[:HAS_CREATOR]->(personB)
     RETURN
        id(personA) AS source,
        id(personB) AS target,
        1.0/count(replyOf) AS weight',
  startNode: person1,
  endNode: person2,
  relationshipWeightProperty: 'weight'
})
YIELD cost
RETURN person1.id, person2.id, max(cost) AS totalWeight
ORDER BY totalWeight DESC, person1.id ASC, person2.id ASC
LIMIT 20
