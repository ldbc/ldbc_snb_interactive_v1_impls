// Q1. Posting summary
/*
  :param { date: '2010-09-14T22:00:00.000+0000' }
*/
MATCH (message:Message)
WHERE message.creationDate <= $date
WITH toFloat(count(message)) AS totalMessageCount // this should be a subquery once Cypher supports it
MATCH (message:Message)
WHERE message.creationDate <= $date
  AND message.content IS NOT NULL
WITH
  totalMessageCount,
  message,
  toInteger(substring(message.creationDate, 0, 4)) AS year,
  length(message.content) AS length,
  [label IN labels(message) WHERE label <> 'Message'][0] AS messageType
WITH
  totalMessageCount,
  year,
  messageType,
  CASE
    WHEN length <  40 THEN 0
    WHEN length <  80 THEN 1
    WHEN length < 160 THEN 2
    ELSE                   3
  END AS lengthCategory,
  count(message) AS messageCount,
  floor(avg(message.length)) AS averageMessageLength,
  sum(message.length) AS sumMessageLength
RETURN
  year,
  messageType,
  lengthCategory,
  messageCount,
  averageMessageLength,
  sumMessageLength,
  messageCount / totalMessageCount AS percentageOfMessages
ORDER BY
  year DESC,
  messageType DESC, // spec says 'ASC', where Posts come first and Comments second
  lengthCategory ASC
