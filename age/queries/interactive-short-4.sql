SET search_path = ag_catalog, public;
SELECT * FROM (
  SELECT * FROM cypher('$graphName', $$
    MATCH (m:Comment {id: $messageId})
    RETURN coalesce(m.content, m.imageFile), m.creationDate
  $$) AS (messageContent agtype, messageCreationDate agtype)
  UNION ALL
  SELECT * FROM cypher('$graphName', $$
    MATCH (m:Post {id: $messageId})
    RETURN coalesce(m.imageFile, m.content), m.creationDate
  $$) AS (messageContent agtype, messageCreationDate agtype)
) msg
LIMIT 1;
