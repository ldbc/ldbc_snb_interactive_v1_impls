// Q7. Most authoritative users on a given topic
/*
  :param { tag: 'Charles_V,_Holy_Roman_Emperor' }
*/
MATCH
  (tag:Tag {name: $tag})<-[:HAS_TAG]-(:Message)-[:HAS_CREATOR]->(person1:Person)
MATCH
  (person1)<-[:HAS_CREATOR]-(message:Message)-[:HAS_TAG]->(tag),
  (message)<-[:LIKES]-(person2:Person)<-[:HAS_CREATOR]-(:Message)<-[l:LIKES]-(person3:Person)
RETURN
  person1.id,
  count(DISTINCT l) AS authorityScore
ORDER BY
  authorityScore DESC,
  person1.id ASC
LIMIT 100
