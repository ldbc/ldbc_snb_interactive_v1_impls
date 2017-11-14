// Q24. Messages by Topic and Continent
/*
  :param { tagClass: 'Single' }
*/
MATCH
  (:TagClass {name: $tagClass})<-[:hasType]-(:Tag)<-[:hasTag]-(message:Message)<-[:likes]-(person:Person),
  (message)-[:isLocatedIn]->(:Country)-[:isPartOf]->(continent:Continent)
WITH
  message,
  person,
  message.creationDate/10000000000000 AS year,
  message.creationDate/100000000000%100 AS month,
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
