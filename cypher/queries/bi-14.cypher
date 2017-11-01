// Q14. Top thread initiators
/*
  :param {
    begin: '2010-10-01T04:00:00.000+0000',
    end: '2010-11-01T04:00:00.000+0000'
  }
*/
MATCH (person:Person)<-[:hasCreator]-(message:Message)<-[:replyOf*]-(reply:Message)
WHERE message.creationDate >= '2010-01-01T00:00:00.000+0000'
  AND message.creationDate <= '2011-01-01T00:00:00.000+0000'
  AND reply.creationDate   >= '2010-01-01T00:00:00.000+0000'
  AND reply.creationDate   <= '2011-01-01T00:00:00.000+0000'
RETURN
  person.id,
  person.firstName,
  person.lastName,
  person.creationDate,
  count(message) AS threadCount,
  count(reply) AS messageCount
ORDER BY
  messageCount DESC,
  person.id ASC
LIMIT 100
