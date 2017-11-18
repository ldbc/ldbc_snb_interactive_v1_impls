// Q6. Most active Posters of a given Topic
/*
  :param { tag: 'Abbas_I_of_Persia' }
*/
MATCH (tag:Tag {name: $tag})<-[:HAS_TAG]-(message:Message)-[:HAS_CREATOR]->(person:Person)
WITH person, message
OPTIONAL MATCH (fan:Person)-[:LIKES]->(message)
WITH person, message, count(fan) AS likeCount
OPTIONAL MATCH (message)<-[:REPLY_OF]-(comment:Comment)
WITH person, message, likeCount, count(comment) AS replyCount
WITH person, sum(likeCount) AS likeCount, sum(replyCount) AS replyCount, count(message) AS messageCount
RETURN
  person.id,
  messageCount,
  replyCount,
  likeCount,
  1*messageCount + 2*replyCount + 10*likeCount AS score
ORDER BY
  score DESC,
  person.id ASC
LIMIT 100
