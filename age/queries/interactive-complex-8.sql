SET search_path = ag_catalog, public;
SELECT * FROM (
  SELECT * FROM cypher('$graphName', $$
    MATCH (p:Person {id: $personId})<-[:HAS_CREATOR]-(msg:Comment)<-[:REPLY_OF]-(reply:Comment)-[:HAS_CREATOR]->(author:Person)
    RETURN author.id, author.firstName, author.lastName, reply.creationDate, reply.id, reply.content
  $$) AS (personId agtype, personFirstName agtype, personLastName agtype,
          commentCreationDate agtype, commentId agtype, commentContent agtype)
  UNION ALL
  SELECT * FROM cypher('$graphName', $$
    MATCH (p:Person {id: $personId})<-[:HAS_CREATOR]-(msg:Post)<-[:REPLY_OF]-(reply:Comment)-[:HAS_CREATOR]->(author:Person)
    RETURN author.id, author.firstName, author.lastName, reply.creationDate, reply.id, reply.content
  $$) AS (personId agtype, personFirstName agtype, personLastName agtype,
          commentCreationDate agtype, commentId agtype, commentContent agtype)
) replies
ORDER BY commentCreationDate DESC, commentId ASC
LIMIT 20;
