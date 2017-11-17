// Q8. Related Topics
/*
  :param { tag: 'Sammy_Sosa' }
*/
MATCH
  (tag:Tag {name: $tag})<-[:HAS_TAG]-(:Message)<-[:REPLY_OF*]-
  (comment:Comment)-[:HAS_TAG]->(relatedTag:Tag)
  // there is no need to filter for relatedTag.name != $tag, as the edge uniqueness constraint takes care of that
WHERE NOT (comment)-[:HAS_TAG]->(tag)
RETURN
  relatedTag.name,
  count(DISTINCT comment) AS count
ORDER BY
  count,
  relatedTag.name
LIMIT 100
