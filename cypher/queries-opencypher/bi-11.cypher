// Q11. Unrelated replies
/*
  :param {
    country: 'Germany',
    blacklist: ['also', 'Pope', 'that', 'James', 'Henry', 'one', 'Green']
  }
*/
WITH $blacklist AS blacklist
MATCH
  (country:Country {name: $country})<-[:IS_PART_OF]-(:City)<-[:IS_LOCATED_IN]-
  (person:Person)<-[:HAS_CREATOR]-(reply:Comment)-[:REPLY_OF]->(message:Message),
  (reply)-[:HAS_TAG]->(tag:Tag)
WHERE NOT (message)-[:HAS_TAG]->(:Tag)<-[:HAS_TAG]-(reply)
  AND size([word IN blacklist WHERE reply.content CONTAINS word | word]) = 0
WITH *
OPTIONAL MATCH
  (:Person)-[like:LIKES]->(reply)
RETURN
  person.id,
  tag.name,
  count(DISTINCT like) AS countLikes,
  count(DISTINCT reply) AS countReplies
ORDER BY
  countLikes DESC,
  person.id ASC,
  tag.name ASC
LIMIT 100
