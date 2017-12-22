// Q7. Most authoritative users on a given topic
/*
  :param { tag: 'Charles_V,_Holy_Roman_Emperor' }
*/
MATCH (tag:Tag {name: $tag})<-[:HAS_TAG]-(message1:Message)-[:HAS_CREATOR]->(person1:Person)
MATCH (person1)<-[:HAS_CREATOR]-(message2:Message)-[:HAS_TAG]->(tag)
OPTIONAL MATCH (message2)<-[:LIKES]-(person2:Person)<-[:HAS_CREATOR]-(message3:Message)<-[like:LIKES]-(:Person)
RETURN
  person1.id,
  count(DISTINCT like) AS authorityScore
ORDER BY
  authorityScore DESC,
  person1.id ASC
LIMIT 100
