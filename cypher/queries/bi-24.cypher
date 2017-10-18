// Q24. Messages by Topic and Continent
// :param tagClass
MATCH
  (:TagClass)<-[:hasType]-(:Tag)<-[:hasTag]-(message:Message)<-[:likes]-(person:Person),
  (message)-[:isLocatedIn]->(:Country)-[:isPartOf]->(continent:Continent)
WITH
  message,
  person,
  toInteger(substring(message.creationDate, 0, 4)) AS year,
  toInteger(substring(message.creationDate, 5, 2)) AS month,
  continent
RETURN
  count(message) AS messageCount,
  count(person) AS likeCount,
  year,
  month,
  continent.name
ORDER BY
  year ASC,
  month ASC,
  continent.name DESC
LIMIT 100
