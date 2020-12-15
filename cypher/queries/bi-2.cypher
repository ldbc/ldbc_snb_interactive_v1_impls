// Q2. Tag evolution
/*
  :param [{ year, month, tagClass }] => { RETURN 2010 AS year, 10 AS month, 'MusicalArtist' AS tagClass }
*/
MATCH (tag:Tag)-[:HAS_TYPE]->(:TagClass {name: $tagClass})
// year-month 1
OPTIONAL MATCH (message1:Message)-[:HAS_TAG]->(tag)
  WHERE message1.creationDate.year = $year
    AND message1.creationDate.month = $month
WITH tag, count(message1) AS countMonth1
// year-month 2
OPTIONAL MATCH (message2:Message)-[:HAS_TAG]->(tag)
  WHERE message2.creationDate.year = $year + toInteger($month / 12)
    AND message2.creationDate.month = $month % 12 + 1
WITH
  tag,
  countMonth1,
  count(message2) AS countMonth2
RETURN
  tag.name,
  countMonth1,
  countMonth2,
  abs(countMonth1-countMonth2) AS diff
ORDER BY
  diff DESC,
  tag.name ASC
LIMIT 100
