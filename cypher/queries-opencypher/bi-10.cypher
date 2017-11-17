// Q10. Central Person for a Tag
/*
  :param {
    tag: 'Franz_Liszt',
    date: 20100822040000000
  }
*/
MATCH (tag:Tag {name: $tag}), (person:Person)
// score
OPTIONAL MATCH (person)-[i:HAS_INTEREST]->(tag)
OPTIONAL MATCH (person)<-[:HAS_CREATOR]-(m:Message)-[:HAS_TAG]->(tag)
WHERE m.creationDate > $date
WITH
  tag,
  person,
  count(i)*100 + count(m) AS score
// friendsScore
OPTIONAL MATCH (person)-[:KNOWS]-(friend:Person)
OPTIONAL MATCH (friend)-[i:HAS_INTEREST]->(tag)
OPTIONAL MATCH (friend)<-[:HAS_CREATOR]-(m:Message)-[:HAS_TAG]->(tag)
WHERE m.creationDate > $date
WITH
  person,
  score,
  count(i)*100 + count(m) AS individualFriendsScore
RETURN
  person.id,
  score,
  sum(individualFriendsScore) AS friendsScore
ORDER BY
  score + friendsScore DESC,
  person.id ASC
LIMIT 100
