// Q12. Expert search
/*
:param [{ personId, tagClassName }] => { RETURN
  10995116278009 AS personId,
  "Monarch" AS tagClassName
}
*/
MATCH
  (:Person {id: $personId})-[:KNOWS]-(friend:Person)<-[:HAS_CREATOR]-(comment:Comment)-[:REPLY_OF]->(:Post)-[:HAS_TAG]->(tag:Tag),
  (tag)-[:HAS_TYPE]->(tagClass:TagClass)-[:IS_SUBCLASS_OF*0..]->(baseTagClass:TagClass)
WHERE baseTagClass.name = $tagClassName
RETURN
  friend.id AS personId,
  friend.firstName AS personFirstName,
  friend.lastName AS personLastName,
  collect(DISTINCT tag.name) AS tagNames,
  count(DISTINCT comment) AS replyCount
ORDER BY replyCount DESC, personId ASC
LIMIT 20
