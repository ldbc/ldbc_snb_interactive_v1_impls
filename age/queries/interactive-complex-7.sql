SET search_path = ag_catalog, public;
SELECT personId, personFirstName, personLastName, likeCreationDate, commentOrPostId,
       commentOrPostContent, minutesLatency, isNew
FROM (
  SELECT DISTINCT ON (personId)
    personId, personFirstName, personLastName, likeCreationDate, commentOrPostId,
    commentOrPostContent, minutesLatency, isNew
  FROM (
    SELECT * FROM cypher('$graphName', $$
      MATCH (p:Person {id: $personId})<-[:HAS_CREATOR]-(msg:Comment)<-[like:LIKES]-(liker:Person)
      WITH liker, msg, like.creationDate AS likeTime
      OPTIONAL MATCH (p)-[:KNOWS]-(liker)
      RETURN liker.id, liker.firstName, liker.lastName, likeTime, msg.id,
             coalesce(msg.content, msg.imageFile),
             toInteger(floor(toFloat(likeTime - msg.creationDate) / 60000.0)),
             CASE WHEN (p)-[:KNOWS]-(liker) THEN false ELSE true END
    $$) AS (personId agtype, personFirstName agtype, personLastName agtype,
            likeCreationDate agtype, commentOrPostId agtype, commentOrPostContent agtype,
            minutesLatency agtype, isNew agtype)
    UNION ALL
    SELECT * FROM cypher('$graphName', $$
      MATCH (p:Person {id: $personId})<-[:HAS_CREATOR]-(msg:Post)<-[like:LIKES]-(liker:Person)
      WITH liker, msg, like.creationDate AS likeTime
      OPTIONAL MATCH (p)-[:KNOWS]-(liker)
      RETURN liker.id, liker.firstName, liker.lastName, likeTime, msg.id,
             coalesce(msg.imageFile, msg.content),
             toInteger(floor(toFloat(likeTime - msg.creationDate) / 60000.0)),
             CASE WHEN (p)-[:KNOWS]-(liker) THEN false ELSE true END
    $$) AS (personId agtype, personFirstName agtype, personLastName agtype,
            likeCreationDate agtype, commentOrPostId agtype, commentOrPostContent agtype,
            minutesLatency agtype, isNew agtype)
  ) all_likes
  ORDER BY personId, likeCreationDate DESC
) latest_likes
ORDER BY likeCreationDate DESC, personId ASC
LIMIT 20;
