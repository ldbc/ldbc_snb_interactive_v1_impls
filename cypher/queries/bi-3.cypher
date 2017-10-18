// Tag evolution
WITH $year AS year, $month AS month
WITH
  year AS year1,
  month AS month1,
  year + toInteger(month / 12) AS year2,
  month % 12 + 1 AS month2
// year-month 1
MATCH (tag:Tag)
OPTIONAL MATCH (message1:Message)-[:hasTag]->(tag)
  WHERE toInteger(substring(message1.creationDate, 0, 4)) = year1
    AND toInteger(substring(message1.creationDate, 5, 2)) = month1
WITH year2, month2, tag, count(message1) AS countMonth1
// year-month 2
OPTIONAL MATCH (message2:Message)-[:hasTag]->(tag)
  WHERE toInteger(substring(message2.creationDate, 0, 4)) = year2
    AND toInteger(substring(message2.creationDate, 5, 2)) = month2
WITH
  tag,
  countMonth1,
  count(message2) AS countMonth2
RETURN
  tag.name,
  countMonth1,
  countMonth2,
  abs(countMonth1-countMonth2) AS diff
ORDER BY diff DESC, tag.name ASC
LIMIT 100
