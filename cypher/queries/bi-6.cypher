// Q6. Most active Posters of a given Topic
/*
  :param { tag: 'Abbas_I_of_Persia' }
*/
MATCH (tag:Tag {name: $tag})<-[:HAS_TAG]-(message:Message)-[:HAS_CREATOR]->(person:Person)
OPTIONAL MATCH (:Person)-[l:LIKES]->(message)
OPTIONAL MATCH (message)<-[:REPLY_OF]-(comment:Comment)
WITH person, count(distinct l) AS likeCount, count(distinct comment) AS replyCount, count(distinct message) AS messageCount
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
