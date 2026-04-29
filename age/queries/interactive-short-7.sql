SET search_path = ag_catalog, public;
SELECT * FROM (
  SELECT * FROM cypher('$graphName', $$
    MATCH (m:Comment {id: $messageId})<-[:REPLY_OF]-(reply:Comment)-[:HAS_CREATOR]->(author:Person)
    OPTIONAL MATCH (m)-[:HAS_CREATOR]->(orig:Person)-[:KNOWS]-(author)
    RETURN reply.id, reply.content, reply.creationDate, author.id, author.firstName, author.lastName,
           orig IS NOT NULL
    ORDER BY reply.creationDate DESC, toInteger(author.id) ASC
  $$) AS (commentId agtype, commentContent agtype, commentCreationDate agtype,
          replyAuthorId agtype, replyAuthorFirstName agtype, replyAuthorLastName agtype,
          replyAuthorKnowsOriginalMessageAuthor agtype)
  UNION ALL
  SELECT * FROM cypher('$graphName', $$
    MATCH (m:Post {id: $messageId})<-[:REPLY_OF]-(reply:Comment)-[:HAS_CREATOR]->(author:Person)
    OPTIONAL MATCH (m)-[:HAS_CREATOR]->(orig:Person)-[:KNOWS]-(author)
    RETURN reply.id, reply.content, reply.creationDate, author.id, author.firstName, author.lastName,
           orig IS NOT NULL
    ORDER BY reply.creationDate DESC, toInteger(author.id) ASC
  $$) AS (commentId agtype, commentContent agtype, commentCreationDate agtype,
          replyAuthorId agtype, replyAuthorFirstName agtype, replyAuthorLastName agtype,
          replyAuthorKnowsOriginalMessageAuthor agtype)
) replies
ORDER BY commentCreationDate DESC, replyAuthorId ASC;
