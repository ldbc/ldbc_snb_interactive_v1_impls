// IS2. Recent messages of a person
/*
:param personId: 10995116277795
 */
MATCH (:Person {id: $personId})<-[:HAS_CREATOR]-(m:Message)-[:REPLY_OF*0..]->(p:Post)
MATCH (p)-[:HAS_CREATOR]->(c)
RETURN
  m.id AS messageId,
  coalesce(m.content, m.imageFile) AS messageContent,
  m.creationDate AS messageCreationDate,
  p.id AS originalPostId,
  c.id AS originalPostAuthorId,
  c.firstName AS originalPostAuthorFirstName,
  c.lastName AS originalPostAuthorLastName
ORDER BY messageCreationDate DESC
LIMIT 10
