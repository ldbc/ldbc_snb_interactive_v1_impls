// Q11. Unrelated replies
/*
  :param {
    country: 'Pakistan',
    blacklist: ['one', 'has', 'David']
  }
*/
WITH $blacklist AS blacklist
MATCH
  (country:Country {name: $country})<-[:IS_PART_OF]-(:City)<-[:IS_LOCATED_IN]-
  (person:Person)<-[:HAS_CREATOR]-(reply:Comment)-[:REPLY_OF]-(message:Message),
  (reply)-[:HAS_TAG]->(tag:Tag)
OPTIONAL MATCH
  (fan:Person)-[:LIKES]->(reply)
WHERE NOT (message)-[:HAS_TAG]->(tag)
  AND size([word IN blacklist WHERE reply.content CONTAINS word | word]) = 0
RETURN
  person.id,
  tag.name,
  count(fan) AS countLikes,
  count(reply) AS countReplies,
  reply.content
ORDER BY
  countLikes DESC,
  person.id ASC,
  tag.name ASC
LIMIT 100
