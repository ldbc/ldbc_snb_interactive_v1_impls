// Q24. Messages by Topic and Continent
/*
  :param { tagClass: 'Single' }
*/
MATCH
  (:TagClass {name: $tagClass})<-[:HAS_TYPE]-(:Tag)<-[:HAS_TAG]-(message:Message)<-[:LIKES]-(person:Person),
  (message)-[:IS_LOCATED_IN]->(:Country)-[:IS_PART_OF]->(continent:Continent)
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
