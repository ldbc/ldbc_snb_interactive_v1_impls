// Q24. Messages by Topic and Continent
/*
  :param { tagClass: 'Single' }
*/
MATCH (:TagClass {name: $tagClass})<-[:HAS_TYPE]-(:Tag)<-[:HAS_TAG]-(message:Message)
WITH DISTINCT message
MATCH (message)-[:IS_LOCATED_IN]->(:Country)-[:IS_PART_OF]->(continent:Continent)
OPTIONAL MATCH (message)<-[like:LIKES]-(:Person)
WITH
  message,
  message.creationDate/10000000000000   AS year,
  message.creationDate/100000000000%100 AS month,
  like,
  continent
RETURN
  count(DISTINCT message) AS messageCount,
  count(like) AS likeCount,
  year,
  month,
  continent.name
ORDER BY
  year ASC,
  month ASC,
  continent.name DESC
LIMIT 100
