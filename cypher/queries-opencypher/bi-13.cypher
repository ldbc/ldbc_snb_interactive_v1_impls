// Q13. Popular Tags per month in a country
/*
  :param { country: 'Burma' }
*/
MATCH (:Country {name: $country})<-[:IS_LOCATED_IN]-(message:Message)-[:HAS_TAG]->(tag:Tag)
WITH
  message.creationDate/10000000000000   AS year,
  message.creationDate/100000000000%100 AS month,
  message,
  tag
WITH year, month, count(message) AS popularity, tag
ORDER BY popularity DESC, tag.name ASC
WITH
  year,
  month,
  collect([tag.name, popularity]) AS popularTags
RETURN
  year,
  month,
  [i IN range(0, (CASE size(popularTags) < 5 WHEN true THEN size(popularTags) ELSE 5 END)-1) 
  | popularTags[i]] AS topPopularTags
ORDER BY
  year DESC,
  month ASC
LIMIT 100
