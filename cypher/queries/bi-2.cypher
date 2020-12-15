// Q2. Tag evolution
/*
  :param [{ date, tagClass }] => { RETURN datetime('2012-01-22') AS date, 'MusicalArtist' AS tagClass }
*/
MATCH (tag:Tag)-[:HAS_TYPE]->(:TagClass {name: $tagClass})
// year-month 1
OPTIONAL MATCH (message1:Message)-[:HAS_TAG]->(tag)
  WHERE $date <= message1.creationDate
    AND message1.creationDate < $date + duration({days: 100})
WITH tag, count(message1) AS countMonth1
// year-month 2
OPTIONAL MATCH (message2:Message)-[:HAS_TAG]->(tag)
  WHERE $date + duration({days: 100}) <= message2.creationDate
    AND message2.creationDate < $date + duration({days: 200})
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
