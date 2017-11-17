// Q23. Holiday destinations
/*
  :param { country: 'Spain' }
*/
MATCH
  (homeCountry:Country {name: $country})<-[:IS_PART_OF]-(:City)<-[:IS_LOCATED_IN]-
  (:Person)<-[:HAS_CREATOR]-(message:Message)-[:IS_LOCATED_IN]->(country:Country)
WHERE homeCountry <> country
WITH
  message,
  country,
  message.creationDate/100000000000%100 AS month,
RETURN
  count(message) AS messageCount,
  country.name,
  month
ORDER BY
  messageCount DESC,
  country.name ASC,
  month DESC
LIMIT 100
