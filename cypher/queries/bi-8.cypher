// Related Topics
MATCH
  (tag:Tag {name: $tag})<-[:hasTag]-(:Message)<-[:replyOf*]-
  (comment:Comment)-[:hasTag]->(relatedTag:Tag)
// there is no need to filter for relatedTag.name != $tag, as it would match
WHERE NOT (comment)-[:hasTag]->(tag)
RETURN relatedTag.name, count(comment) AS count
ORDER BY count, relatedTag.name
