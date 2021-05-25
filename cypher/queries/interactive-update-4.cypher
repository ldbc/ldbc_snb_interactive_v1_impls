MATCH (p:Person {id: $moderatorPersonId})
CREATE (f:Forum {id: $forumId, title: $forumTitle, creationDate: datetime($creationDate)})-[:HAS_MODERATOR]->(p)
WITH f
UNWIND $tagIds AS tagId
  MATCH (t:Tag {id: tagId})
  CREATE (f)-[:HAS_TAG]->(t)
