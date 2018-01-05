// Q23. Holiday destinations
/*
  :param { country: 'Egypt' }
*/
MATCH
  (home:Country {name: $country})<-[:IS_PART_OF]-(:City)<-[:IS_LOCATED_IN]-
  (:Person)<-[:HAS_CREATOR]-(message:Message)-[:IS_LOCATED_IN]->(destination:Country)
WHERE home <> destination
WITH
  message,
  destination,
  message.creationDate/100000000000%100 AS month
RETURN
  count(message) AS messageCount,
  destination.name,
  month
ORDER BY
  messageCount DESC,
  destination.name ASC,
  month ASC
LIMIT 100
