SET search_path = ag_catalog, public;
SELECT * FROM (
  SELECT * FROM cypher('$graphName', $$
    MATCH (p:Person {id: $personId})<-[:HAS_CREATOR]-(msg:Comment)
    WITH msg ORDER BY msg.creationDate DESC, msg.id ASC LIMIT 10
    MATCH (msg)-[:REPLY_OF*0..]->(post:Post)-[:HAS_CREATOR]->(author:Person)
    RETURN msg.id, coalesce(msg.content, msg.imageFile), msg.creationDate,
           post.id, author.id, author.firstName, author.lastName
  $$) AS (messageId agtype, messageContent agtype, messageCreationDate agtype,
          originalPostId agtype, originalPostAuthorId agtype,
          originalPostAuthorFirstName agtype, originalPostAuthorLastName agtype)
  UNION ALL
  SELECT * FROM cypher('$graphName', $$
    MATCH (p:Person {id: $personId})<-[:HAS_CREATOR]-(msg:Post)
    WITH msg ORDER BY msg.creationDate DESC, msg.id ASC LIMIT 10
    MATCH (msg)-[:HAS_CREATOR]->(author:Person)
    RETURN msg.id, coalesce(msg.imageFile, msg.content), msg.creationDate,
           msg.id, author.id, author.firstName, author.lastName
  $$) AS (messageId agtype, messageContent agtype, messageCreationDate agtype,
          originalPostId agtype, originalPostAuthorId agtype,
          originalPostAuthorFirstName agtype, originalPostAuthorLastName agtype)
) all_msgs
ORDER BY messageCreationDate DESC, messageId DESC
LIMIT 10;
