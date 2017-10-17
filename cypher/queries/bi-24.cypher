// Messages by Topic and Continent
MATCH
  (:TagClass)<-[:hasType]-(:Tag)<-[:hasTag]-(message:Message)<-[:likes]-(person:Person),
  (message)-[:isLocatedIn]->(:Country)-[:isPartOf]->(continent:Continent)
WITH
  message,
  person,
  toInt(substring(message.creationDate, 0, 4)) AS year,
  toInt(substring(message.creationDate, 5, 2)) AS month,
  continent
RETURN
  count(message) AS messageCount,
  count(person) AS likeCount,
  year,
  month,
  continent.name
ORDER BY messageCount, likeCount, year, month, continent.name
LIMIT 100
