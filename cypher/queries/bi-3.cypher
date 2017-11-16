// Q3. Tag evolution
/*
  :param {
    year: 2010,
    month: 6
  }
*/

// Cleaner
WITH $month % 12 + 1 AS month2,
     $year + toInteger($month / 12.0) AS year2
// year-month 1
MATCH (tag:Tag)
OPTIONAL MATCH (message1:Message)-[:COMMENT_HAS_TAG|POST_HAS_TAG]->(tag)
WHERE apoc.date.field(message1.creationDate,'month')=$month AND
      apoc.date.field(message1.creationDate,'year')=$year
WITH year2, month2, tag, count(message1) AS countMonth1
// year-month 2
OPTIONAL MATCH (message2:Message)-[:COMMENT_HAS_TAG|POST_HAS_TAG]->(tag)
WHERE apoc.date.field(message2.creationDate,'month')=month2 AND
      apoc.date.field(message2.creationDate,'year')=year2
WITH tag, countMonth1, count(message2) AS countMonth2
RETURN
  tag.name,
  countMonth1,
  countMonth2,
  abs(countMonth1-countMonth2) AS diff
ORDER BY
  diff DESC,
  tag.name ASC
LIMIT 100

// Faster: (1) performs many fewer date parsing operations (2) potentially enabled index scan
WITH $month % 12 + 1 AS month2,
     $year + toInteger($month / 12.0) AS year2
WITH month2,
     year2,
     month2 % 12 + 1 AS month3,
     year2 + toInteger(month2 / 12.0) AS year3
WITH apoc.date.parse(toInt($year)+'-'+right('0'+toInt($month),2)+'-01','ms','yyyy-MM-dd') AS min1,
     apoc.date.parse(toInt(year2)+'-'+right('0'+toInt(month2),2)+'-01','ms','yyyy-MM-dd') AS max1,
     apoc.date.parse(toInt(year2)+'-'+right('0'+toInt(month2),2)+'-01','ms','yyyy-MM-dd') AS min2,
     apoc.date.parse(toInt(year3)+'-'+right('0'+toInt(month3),2)+'-01','ms','yyyy-MM-dd') AS max2
// year-month 1
MATCH (tag:Tag)
OPTIONAL MATCH (message1:Message)-[:COMMENT_HAS_TAG|POST_HAS_TAG]->(tag)
WHERE message1.creationDate>=min1 AND message1.creationDate<max1
WITH min2, max2, tag, count(message1) AS countMonth1
// year-month 2
OPTIONAL MATCH (message2:Message)-[:COMMENT_HAS_TAG|POST_HAS_TAG]->(tag)
WHERE message2.creationDate>=min2 AND message2.creationDate<max2
WITH  tag,  countMonth1, count(message2) AS countMonth2
RETURN
  tag.name,
  countMonth1,
  countMonth2,
  abs(countMonth1-countMonth2) AS diff
ORDER BY
  diff DESC,
  tag.name ASC
LIMIT 100
