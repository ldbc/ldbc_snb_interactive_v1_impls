// Posting summary
MATCH (message:Message)
WHERE message.creationDate <= $date
UNWIND labels(message) AS label
WITH
  message,
  toInt(substring(message.creationDate, 0, 4)) AS year,
  length(message.content) AS length,
  label
WITH
  message,
  year,
  CASE 
    WHEN length < 40 THEN 'short'
    WHEN length < 80 THEN 'one liner'
    WHEN length < 160 THEN 'tweet'
    ELSE 'long'
  END AS lengthCategory,
  label
WHERE label <> 'Message'
RETURN
  year,
  label AS messageType,
  lengthCategory,
  count(message) AS messageCount,
  avg(message.length) AS averageMessageLength,
  sum(message.length) AS sumMessageLength
ORDER BY year DESC, messageType ASC, lengthCategory ASC
LIMIT 100
