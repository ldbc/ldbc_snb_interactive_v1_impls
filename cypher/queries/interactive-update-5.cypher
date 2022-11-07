OPTIONAL MATCH (f:Forum {id: $forumId}), (p:Person {id: $personId})
WITH 1/count(f) AS testWhetherForumFound, 1/count(p) AS testWhetherPersonFound
MATCH (f:Forum {id: $forumId}), (p:Person {id: $personId})
CREATE (f)-[:HAS_MEMBER {creationDate: $creationDate}]->(p)
