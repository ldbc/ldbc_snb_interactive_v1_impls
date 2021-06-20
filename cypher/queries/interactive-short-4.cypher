MATCH (m:Message {id: $messageId})
RETURN
  m.creationDate AS messageCreationDate,
  coalesce(m.content, m.imageFile) AS messageContent
