// Q11. Unrelated replies
/*
  :param {
    country: 'Pakistan',
    blacklist: ['one', 'has', 'David']
  }
*/
WITH $blacklist AS blacklist
MATCH
  (country:Country {name: $country})<-[:isPartOf]-(:City)<-[:isLocatedIn]-
  (person:Person)<-[:hasCreator]-(message:Message)<-[:replyOf]-(reply:Comment),
  (message)-[:hasTag]->(tag:Tag),
  (fan:Person)-[:likes]->(reply)
WHERE NOT (tag)<-[:hasTag]-(reply)
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
