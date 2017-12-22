// Q10. Central Person for a Tag
/*
  :param {
    tag: 'Che_Guevara',
    date: 20110721220000000
  }
*/
MATCH (tag:Tag {name: $tag})
// score
OPTIONAL MATCH (tag)<-[interest:HAS_INTEREST]-(person:Person)
OPTIONAL MATCH (tag)<-[:HAS_TAG]-(message:Message)-[:HAS_CREATOR]->(person:Person)
WHERE message.creationDate > $date
  AND person IS NOT NULL
WITH
  tag,
  person,
  count(interest)*100 + count(message) AS score
// friendsScore
MATCH (person)-[:KNOWS]-(friend:Person)
OPTIONAL MATCH (tag)<-[interest:HAS_INTEREST]-(friend)
OPTIONAL MATCH (tag)<-[:HAS_TAG]-(message:Message)-[:HAS_CREATOR]->(friend)
WHERE message.creationDate > $date
RETURN
  person.id,
  score,
  count(interest)*100 + count(message) AS friendsScore
ORDER BY
  score + friendsScore DESC,
  person.id ASC
LIMIT 100
