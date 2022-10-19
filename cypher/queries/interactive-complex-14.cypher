// Q14. Trusted connection paths
// Requires the Neo4j Graph Data Science library
/*
:param [{ person1Id, person2Id }] => { RETURN
  14 AS person1Id,
  27 AS person2Id
}
*/
CALL gds.graph.drop('q14graph', false)
YIELD graphName

// ----------------------------------------------------------------------------------------------------
WITH count(*) AS dummy
// ----------------------------------------------------------------------------------------------------

CALL gds.graph.project.cypher(
  'q14graph',
  'MATCH (p:Person) RETURN id(p) AS id',
  'MATCH
      (pA:Person)-[knows:KNOWS]-(pB:Person),
      (pA)<-[:HAS_CREATOR]-(m1:Message)-[r:REPLY_OF]-(m2:Message)-[:HAS_CREATOR]->(pB)
    WITH
      id(pA) AS source,
      id(pB) AS target,
      count(r) AS numInteractions
    RETURN
      source,
      target,
      CASE WHEN floor(40-sqrt(numInteractions)) > 1 THEN floor(40-sqrt(numInteractions)) ELSE 1 END AS weight'
)
YIELD graphName

// ----------------------------------------------------------------------------------------------------
WITH count(*) AS dummy
// ----------------------------------------------------------------------------------------------------

MATCH (person1:Person {id: $person1Id}), (person2:Person {id: $person2Id})
CALL gds.shortestPath.dijkstra.stream(
    'q14graph', {sourceNode: person1, targetNode: person2, relationshipWeightProperty: 'weight'}
)
YIELD index, sourceNode, targetNode, totalCost, nodeIds, costs, path

RETURN [person IN nodes(path) | person.id] AS personIdsInPath, totalCost AS pathWeight
LIMIT 1
