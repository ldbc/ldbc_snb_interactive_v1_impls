// Q19. Interaction path between cities
// Requires the Neo4j Graph Data Science Library
/*
  :param [{city1, city2}] => {RETURN 'Astana' AS city1, 'Athens' AS city2}
*/
MATCH
  (person1:Person)-[:IS_LOCATED_IN]->(city1:City {name: $city1}),
  (person2:Person)-[:IS_LOCATED_IN]->(city2:City {name: $city2})
CALL gds.alpha.shortestPath.stream({
  nodeQuery: 'MATCH (p:Person) RETURN id(p) AS id',
  relationshipQuery:
    'MATCH
       (personA:Person)-[:KNOWS]-(personB:Person),
       (personA)<-[:HAS_CREATOR]-(c:Comment)-[replyOf:REPLY_OF]->(m:Message)-[:HAS_CREATOR]->(personB)
     RETURN
        id(personA) AS source,
        id(personB) AS target,
        1.0/count(replyOf) AS weight',
  startNode: person1,
  endNode: person2,
  relationshipWeightProperty: 'weight'
})
YIELD cost
RETURN person1.id, person2.id, sum(cost) AS totalWeight
ORDER BY totalWeight DESC, person1.id ASC
LIMIT 20
