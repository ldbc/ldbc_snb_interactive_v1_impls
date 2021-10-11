// Q13. Single shortest path
/*
:param [{ person1Id, person2Id }] => { RETURN
  8796093022390 AS person1Id,
  8796093022357 AS person2Id
}
*/
MATCH (person1:Person {id: $person1Id}), (person2:Person {id: $person2Id})
CALL gds.shortestPath.dijkstra.stream({
  nodeQuery: 'MATCH (p:Person) RETURN id(p) AS id',
  relationshipQuery:
    'MATCH
       (personA:Person)-[:KNOWS]-(personB:Person)
     RETURN
       id(personA) AS source,
       id(personB) AS target',
  sourceNode: person1,
  targetNode: person2,
  relationshipWeightProperty: null
})
YIELD totalCost
WITH collect(totalCost) AS costs
RETURN
  CASE WHEN costs = []
    THEN -1
    ELSE toInteger(costs[0])
  END
    AS shortestPathLength
