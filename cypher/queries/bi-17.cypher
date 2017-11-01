// Q17. Friend triangles
/*
  :param { country: 'Spain' }
*/
MATCH (country:Country {name: $country})
MATCH (a:Person)-[:isLocatedIn]->(:City)-[:isPartOf]->(country)
MATCH (b:Person)-[:isLocatedIn]->(:City)-[:isPartOf]->(country)
MATCH (c:Person)-[:isLocatedIn]->(:City)-[:isPartOf]->(country)
MATCH (a)-[:knows]-(b), (b)-[:knows]-(c), (c)-[:knows]-(a)
WHERE a.id < b.id
  AND b.id < c.id
RETURN count(*)
// as a less elegant solution, count(a) also works
