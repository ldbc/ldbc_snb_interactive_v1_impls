// IS4. Content of a message
/*
:param messageId: 206158431836
 */
MATCH (m:Message {id: $messageId})
RETURN
  m.creationDate AS messageCreationDate,
  coalesce(m.content, m.imageFile) AS messageContent
