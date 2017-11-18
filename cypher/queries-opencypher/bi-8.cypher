// Q8. Related Topics
/*
  :param { tag: 'Genghis_Khan' }
*/
MATCH
  (tag:Tag {name: $tag})<-[:HAS_TAG]-(message:Message),
  (message)<-[:REPLY_OF]-(comment:Comment)-[:HAS_TAG]->(relatedTag:Tag)
  // there is no need to filter for relatedTag.name != $tag, as the edge uniqueness constraint takes care of that
WHERE NOT (comment)-[:HAS_TAG]->(tag)
RETURN
  relatedTag.name,
  count(DISTINCT comment) AS count
ORDER BY
  count DESC,
  relatedTag.name ASC
LIMIT 100
