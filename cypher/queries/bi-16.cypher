// Q16. Fake news detection
MATCH 
  (tag:Tag {name: $tagX}),
  (person1)<-[:HAS_CREATOR]-(message1:Message)-[:HAS_TAG]->(tag),
  (person2)<-[:HAS_CREATOR]-(message2:Message)-[:HAS_TAG]->(tag)
WHERE message1.creationDate.day = $dayX
  AND message2.creationDate.day = $dayX
RETURN person1, person2  
