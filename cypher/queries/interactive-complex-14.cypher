MATCH path = allShortestPaths((person1:Person {id:$person1Id})-[:KNOWS*..15]-(person2:Person {id:$person2Id}))
WITH nodes(path) AS pathNodes
RETURN
  extract(n IN pathNodes | n.id) AS personIdsInPath,
  reduce(weight=0.0, idx IN range(1,size(pathNodes)-1) | extract(prev IN [pathNodes[idx-1]] | extract(curr IN [pathNodes[idx]] | weight + length((curr)<-[:HAS_CREATOR]-(:Comment)-[:REPLY_OF]->(:Post)-[:HAS_CREATOR]->(prev))*1.0 + length((prev)<-[:HAS_CREATOR]-(:Comment)-[:REPLY_OF]->(:Post)-[:HAS_CREATOR]->(curr))*1.0 + length((prev)-[:HAS_CREATOR]-(:Comment)-[:REPLY_OF]-(:Comment)-[:HAS_CREATOR]-(curr))*0.5) )[0][0]) AS pathWight
ORDER BY pathWight DESC
