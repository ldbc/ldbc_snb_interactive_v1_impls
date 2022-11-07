OPTIONAL MATCH (person:Person {id: $personId}), (comment:Comment {id: $commentId})
WITH 1/count(person) AS testWhetherPersonFound, 1/count(comment) AS testWhetherCommentFound
MATCH (person:Person {id: $personId}), (comment:Comment {id: $commentId})
CREATE (person)-[:LIKES {creationDate: $creationDate}]->(comment)
