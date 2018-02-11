// Q20. High-level topics
/*
  :param { tagClasses: ['Writer', 'Single', 'Country'] }
*/
UNWIND $tagClasses AS tagClassName
MATCH
  (tagClass:TagClass {name: tagClassName})<-[:IS_SUBCLASS_OF*0..]-
  (:TagClass)<-[:HAS_TYPE]-(tag:Tag)<-[:HAS_TAG]-(message:Message)
RETURN
  tagClass.name,
  count(message) AS messageCount
ORDER BY
  messageCount DESC,
  tagClass.name ASC
LIMIT 100
