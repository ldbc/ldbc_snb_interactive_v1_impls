// Q13. Popular Tags per month in a country
/*
  :param { country: 'Spain' }
*/
MATCH (:Country {name: $country})<-[:isLocatedIn]-(message:Message)-[:hasTag]->(tag:Tag)
WITH
  message.creationDate/10000000000000   AS year,
  message.creationDate/100000000000%100 AS month,
  message,
  tag
WITH year, month, count(message) AS popularity, tag
ORDER BY popularity DESC, tag.name ASC
RETURN
  year,
  month,
  collect([tag.name, popularity]) AS popularTags
ORDER BY
  year DESC,
  month ASC
LIMIT 100
