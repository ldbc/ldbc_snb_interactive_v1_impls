MATCH (m:Message {id:$messageId})
RETURN
  m.creationDate as messageCreationDate,
  CASE exists(m.content)
    WHEN true THEN m.content
    ELSE m.imageFile
  END AS messageContent
