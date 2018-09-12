MATCH (m:Message {id:{id}})
RETURN
  CASE exists(m.content)
    WHEN true THEN m.content
    ELSE m.imageFile
  END AS messageContent,
  m.creationDate as messageCreationDate;
