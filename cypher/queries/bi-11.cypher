// Q11. Friend triangles
/*
  :param [{ country, startDate }] => { RETURN 'Spain' AS country, datetime('2012-05-31') AS startDate }
*/
MATCH (country:Country {name: $country})
MATCH (a:Person)-[:IS_LOCATED_IN]->(:City)-[:IS_PART_OF]->(country)
MATCH (b:Person)-[:IS_LOCATED_IN]->(:City)-[:IS_PART_OF]->(country)
MATCH (c:Person)-[:IS_LOCATED_IN]->(:City)-[:IS_PART_OF]->(country)
MATCH (a)-[k1:KNOWS]-(b), (b)-[k2:KNOWS]-(c), (c)-[k3:KNOWS]-(a)
WHERE a.id < b.id
  AND b.id < c.id
  AND $startDate <= k1.creationDate
  AND $startDate <= k2.creationDate
  AND $startDate <= k3.creationDate
RETURN count(*) AS count
