// Q14. Top thread initiators
/*
  :param {
    begin: 20101001040000000,
    end: 20101101040000000
  }
*/
MATCH (person:Person)<-[:hasCreator]-(message:Message)<-[:replyOf*]-(reply:Message)
WHERE message.creationDate >= $begin
  AND message.creationDate <= $end
  AND reply.creationDate   >= $begin
  AND reply.creationDate   <= $end
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
