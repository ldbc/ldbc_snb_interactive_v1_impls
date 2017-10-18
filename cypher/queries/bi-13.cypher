// Q13. Popular Tags per month in a country
// :param country
MATCH (:Country)<-[:isLocatedIn]-(message:Message)-[:hasTag]->(tag:Tag)
WITH
  toInteger(substring(message.creationDate, 0, 4)) AS year,
  toInteger(substring(message.creationDate, 5, 2)) AS month,
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
