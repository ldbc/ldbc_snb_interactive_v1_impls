// Q12. Trending Posts
/*
  :param {
    date: '2010-08-22T04:00:00.000+0000',
    likeThreshold: 400
  }
*/
MATCH
  (message:Message)-[:hasCreator]->(creator:Person),
  (message)<-[:likes]-(fan:Person)
WHERE message.creationDate > $date
WITH message, creator, count(fan) AS likeCount
WHERE likeCount > $likeThreshold
RETURN
  message.id,
  message.creationDate,
  creator.firstName,
  creator.lastName,
  likeCount
ORDER BY
  likeCount DESC,
  message.id ASC
LIMIT 100
