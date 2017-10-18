// Q1. Posting summary
// :param date: '2011-07-22T04:00:00.000+0000'
MATCH (message:Message)
WHERE message.creationDate <= $date
WITH toFloat(count(message)) AS totalMessageCount // this should be a subquery once Cypher supports it
MATCH (message:Message)
WHERE message.creationDate <= $date
UNWIND labels(message) AS label
WITH
  totalMessageCount,
  message,
  toInteger(substring(message.creationDate, 0, 4)) AS messageYear,
  length(message.content) AS length,
  label
WITH
  totalMessageCount,
  message,
  messageYear,
  CASE
    WHEN length < 40 THEN 'short'
    WHEN length < 80 THEN 'one liner'
    WHEN length < 160 THEN 'tweet'
    ELSE 'long'
  END AS lengthCategory,
  label
WHERE label <> 'Message'
WITH
  totalMessageCount,
  messageYear,
  label AS messageType,
  lengthCategory,
  count(message) AS messageCount,
  avg(message.length) AS averageMessageLength,
  sum(message.length) AS sumMessageLength
RETURN
  messageYear,
  messageType,
  lengthCategory,
  messageCount,
  averageMessageLength,
  sumMessageLength,
  messageCount / totalMessageCount AS percentageOfMessages
ORDER BY
  messageYear DESC,
  messageType ASC,
  lengthCategory ASC
