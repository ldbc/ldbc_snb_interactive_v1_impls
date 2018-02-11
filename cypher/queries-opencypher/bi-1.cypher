// Q1. Posting summary
/*
  :param { date: 20110721220000000 }
*/
MATCH (message:Message)
WHERE message.creationDate < $date
WITH toFloat(count(message)) AS totalMessageCount // this should be a subquery once Cypher supports it
MATCH (message:Message)
WHERE message.creationDate < $date
  AND message.content IS NOT NULL
WITH
  totalMessageCount,
  message,
  message.creationDate/10000000000000 AS year
WITH
  totalMessageCount,
  year,
  message:Comment AS isComment,
  CASE
    WHEN message.length <  40 THEN 0
    WHEN message.length <  80 THEN 1
    WHEN message.length < 160 THEN 2
    ELSE                           3
  END AS lengthCategory,
  count(message) AS messageCount,
  floor(avg(message.length)) AS averageMessageLength,
  sum(message.length) AS sumMessageLength
RETURN
  year,
  isComment,
  lengthCategory,
  messageCount,
  averageMessageLength,
  sumMessageLength,
  messageCount / totalMessageCount AS percentageOfMessages
ORDER BY
  year DESC,
  isComment ASC,
  lengthCategory ASC
