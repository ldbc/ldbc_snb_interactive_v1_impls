// Q14. Trusted connection paths
// Requires the Neo4j Graph Data Science library
/*
:params { person1Id: 14, person2Id: 27 }
*/
// Check whether a path exists -- if there is no path, the query will return an empty result
MATCH
    path = shortestPath((person1 {id: $person1Id})-[:KNOWS*]-(person2 {id: $person2Id}))

// ----------------------------------------------------------------------------------------------------
// the actual values are not important,
// we are only interested in whether there is a row or not
WITH 42 AS dummy
// ----------------------------------------------------------------------------------------------------

MATCH (person1:Person {id: $person1Id}), (person2:Person {id: $person2Id})
CALL gds.graph.project.cypher(
  apoc.create.uuidBase64(),
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
      CASE WHEN round(40-sqrt(numInteractions)) > 1 THEN round(40-sqrt(numInteractions)) ELSE 1 END AS weight
  '
)
YIELD graphName

// ----------------------------------------------------------------------------------------------------
WITH person1, person2, graphName
// ----------------------------------------------------------------------------------------------------

CALL gds.shortestPath.dijkstra.stream(
    graphName, {sourceNode: person1, targetNode: person2, relationshipWeightProperty: 'weight'}
)
YIELD index, sourceNode, targetNode, totalCost, nodeIds, costs, path

WITH path, totalCost, graphName

CALL gds.graph.drop(graphName, false)
YIELD graphName as graphNameremoved

RETURN [person IN nodes(path) | person.id] AS personIdsInPath, totalCost AS pathWeight
LIMIT 1
