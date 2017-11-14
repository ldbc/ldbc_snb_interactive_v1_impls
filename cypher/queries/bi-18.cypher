// Q18. How many persons have a given number of posts
/*
  :param {
    date: 20100822040000000,
    lengthThreshold: 240,
    languages: ['ar']
  }
*/
// note: thresholds like 20 are way too low
MATCH
  (person:Person)<-[:hasCreator]-(message:Message)<-[:replyOf*0..]-(post:Post)
WHERE message.content IS NOT NULL
  AND message.length <= $lengthThreshold
  AND message.creationDate > $date
  AND post.language IN $languages
WITH person, count(message) AS messageCount
RETURN messageCount, count(person) AS personCount
ORDER BY messageCount DESC
LIMIT 100
