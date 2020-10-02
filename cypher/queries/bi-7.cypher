// Q7. Most authoritative users on a given topic
/*
  :param tag => 'Arnold_Schwarzenegger'
*/
MATCH (tag:Tag {name: $tag})<-[:HAS_TAG]-(message2:Message)-[:HAS_CREATOR]->(person1)
OPTIONAL MATCH (message2)<-[:LIKES]-(person2:Person)
MATCH (person2)<-[:HAS_CREATOR]-(message3:Message)<-[like:LIKES]-(p3:Person)
RETURN
  person1.id,
  count(DISTINCT like) AS authorityScore // 'DISTINCT like' ensures that each p2's popularity score is only added once for each p1
ORDER BY
  authorityScore DESC,
  person1.id ASC
LIMIT 100
