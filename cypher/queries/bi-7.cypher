// Q7. Most authoritative users on a given topic
/*
  :param tag: 'Arnold_Schwarzenegger'
*/
MATCH (tag:Tag {name: $tag})
MATCH (tag)<-[:HAS_TAG]-(message1:Message)-[:HAS_CREATOR]->(person1:Person)
MATCH (tag)<-[:HAS_TAG]-(message2:Message)-[:HAS_CREATOR]->(person1)
OPTIONAL MATCH (message2)<-[:LIKES]-(person2:Person)
OPTIONAL MATCH (person2)<-[:HAS_CREATOR]-(message3:Message)<-[like:LIKES]-(p3:Person)
RETURN
  person1.id,
  count(DISTINCT like) AS authorityScore
ORDER BY
  authorityScore DESC,
  person1.id ASC
LIMIT 100
