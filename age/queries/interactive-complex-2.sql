SET search_path = ag_catalog, public;
SELECT * FROM (
  SELECT * FROM cypher('$graphName', $$
    MATCH (p:Person {id: $personId})-[:KNOWS]->(friend:Person)<-[:HAS_CREATOR]-(comment:Comment)
    WHERE comment.creationDate <= $maxDate
    RETURN friend.id, friend.firstName, friend.lastName, comment.id,
           coalesce(comment.content, comment.imageFile), comment.creationDate
  $$) AS (personId agtype, personFirstName agtype, personLastName agtype,
          postOrCommentId agtype, postOrCommentContent agtype, postOrCommentCreationDate agtype)
  UNION ALL
  SELECT * FROM cypher('$graphName', $$
    MATCH (p:Person {id: $personId})-[:KNOWS]->(friend:Person)<-[:HAS_CREATOR]-(post:Post)
    WHERE post.creationDate <= $maxDate
    RETURN friend.id, friend.firstName, friend.lastName, post.id,
           coalesce(post.imageFile, post.content), post.creationDate
  $$) AS (personId agtype, personFirstName agtype, personLastName agtype,
          postOrCommentId agtype, postOrCommentContent agtype, postOrCommentCreationDate agtype)
) messages
ORDER BY postOrCommentCreationDate DESC, postOrCommentId ASC
LIMIT 20;
