// Q10. Central Person for a Tag
/*
  :param {
    tag: 'Franz_Liszt',
    date: 20100822040000000
  }
*/
MATCH (tag:Tag {name: $tag}), (person:Person)
// score
OPTIONAL MATCH (person)-[i:hasInterest]->(tag)
OPTIONAL MATCH (person)<-[:hasCreator]-(m:Message)-[:hasTag]->(tag)
WHERE m.creationDate > $date
WITH
  tag,
  person,
  count(i)*100 + count(m) AS score
// friendsScore
OPTIONAL MATCH (person)-[:knows]-(friend:Person)
OPTIONAL MATCH (friend)-[i:hasInterest]->(tag)
OPTIONAL MATCH (friend)<-[:hasCreator]-(m:Message)-[:hasTag]->(tag)
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
