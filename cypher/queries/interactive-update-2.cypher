MATCH (person:Person {id: $personId}), (post:Post {id: $postId})
CREATE (person)-[:LIKES {creationDate: datetime($creationDate)}]->(post)
