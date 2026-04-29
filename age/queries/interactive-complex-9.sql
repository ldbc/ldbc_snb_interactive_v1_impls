SET search_path = ag_catalog, public;
SELECT * FROM (
  SELECT * FROM cypher('$graphName', $$
    MATCH (p:Person {id: $personId})-[:KNOWS]->(friend:Person)<-[:HAS_CREATOR]-(msg:Comment)
    WHERE msg.creationDate < $maxDate AND friend.id <> $personId
    RETURN friend.id, friend.firstName, friend.lastName, msg.id,
           coalesce(msg.content, msg.imageFile), msg.creationDate
  $$) AS (personId agtype, personFirstName agtype, personLastName agtype,
          messageId agtype, messageContent agtype, messageCreationDate agtype)
  UNION ALL
  SELECT * FROM cypher('$graphName', $$
    MATCH (p:Person {id: $personId})-[:KNOWS]->(friend:Person)<-[:HAS_CREATOR]-(msg:Post)
    WHERE msg.creationDate < $maxDate AND friend.id <> $personId
    RETURN friend.id, friend.firstName, friend.lastName, msg.id,
           coalesce(msg.imageFile, msg.content), msg.creationDate
  $$) AS (personId agtype, personFirstName agtype, personLastName agtype,
          messageId agtype, messageContent agtype, messageCreationDate agtype)
  UNION ALL
  SELECT * FROM cypher('$graphName', $$
    MATCH (p:Person {id: $personId})-[:KNOWS]->(:Person)-[:KNOWS]->(friend:Person)
    WHERE friend.id <> $personId
    OPTIONAL MATCH (p)-[direct:KNOWS]->(friend)
    WITH DISTINCT friend, direct WHERE direct IS NULL
    MATCH (friend)<-[:HAS_CREATOR]-(msg:Comment)
    WHERE msg.creationDate < $maxDate
    RETURN friend.id, friend.firstName, friend.lastName, msg.id,
           coalesce(msg.content, msg.imageFile), msg.creationDate
  $$) AS (personId agtype, personFirstName agtype, personLastName agtype,
          messageId agtype, messageContent agtype, messageCreationDate agtype)
  UNION ALL
  SELECT * FROM cypher('$graphName', $$
    MATCH (p:Person {id: $personId})-[:KNOWS]->(:Person)-[:KNOWS]->(friend:Person)
    WHERE friend.id <> $personId
    OPTIONAL MATCH (p)-[direct:KNOWS]->(friend)
    WITH DISTINCT friend, direct WHERE direct IS NULL
    MATCH (friend)<-[:HAS_CREATOR]-(msg:Post)
    WHERE msg.creationDate < $maxDate
    RETURN friend.id, friend.firstName, friend.lastName, msg.id,
           coalesce(msg.imageFile, msg.content), msg.creationDate
  $$) AS (personId agtype, personFirstName agtype, personLastName agtype,
          messageId agtype, messageContent agtype, messageCreationDate agtype)
) recent_messages
ORDER BY messageCreationDate DESC, messageId ASC
LIMIT 20;
