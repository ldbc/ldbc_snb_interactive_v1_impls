// Most active Posters of a given Topic
MATCH (:Tag)<-[:hasTag]-(message:Message)-[:hasCreator]->(person: Person),
  (message)<-[:likes]-(fan:Person),
  (message)<-[:replyOf*]-(comment:Comment)
WITH person, count(message) AS postCount, count(comment) AS replyCount, count(fan) AS likeCount
RETURN person.id, postCount, replyCount, likeCount, 1*postCount+2*replyCount+10*likeCount AS score
ORDER BY likeCount DESC, person.id ASC
LIMIT 100
