OPTIONAL MATCH (person:Person {id: $personId}), (post:Post {id: $postId})
WITH 1/count(person) AS testWhetherPersonFound, 1/count(post) AS testWhetherPostFound
MATCH (person:Person {id: $personId}), (post:Post {id: $postId})
CREATE (person)-[:LIKES {creationDate: $creationDate}]->(post)
