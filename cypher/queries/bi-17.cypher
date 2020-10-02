// Q17. Friend triangles
/*
  :param country => 'Spain'
*/
MATCH (country:Country {name: $country})
MATCH (a:Person)-[:IS_LOCATED_IN]->(:City)-[:IS_PART_OF]->(country)
MATCH (b:Person)-[:IS_LOCATED_IN]->(:City)-[:IS_PART_OF]->(country)
MATCH (c:Person)-[:IS_LOCATED_IN]->(:City)-[:IS_PART_OF]->(country)
MATCH (a)-[:KNOWS]-(b), (b)-[:KNOWS]-(c), (c)-[:KNOWS]-(a)
WHERE a.id < b.id
  AND b.id < c.id
RETURN count(*) AS count
// as a less elegant solution, count(a) also works
