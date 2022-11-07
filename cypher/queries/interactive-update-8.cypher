OPTIONAL MATCH (p1:Person {id: $person1Id}), (p2:Person {id: $person2Id})
WITH 1/count(p1) AS testWhetherP1Found, 1/count(p2) AS testWhetherP2Found
MATCH (p1:Person {id: $person1Id}), (p2:Person {id: $person2Id})
CREATE (p1)-[:KNOWS {creationDate: $creationDate}]->(p2)
