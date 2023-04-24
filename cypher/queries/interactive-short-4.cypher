// IS4. Content of a message
/*
:params { messageId: 206158431836 }
*/
MATCH (m:Message {id: $messageId })
RETURN
    m.creationDate as messageCreationDate,
    coalesce(m.content, m.imageFile) as messageContent
