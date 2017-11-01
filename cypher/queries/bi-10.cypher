// Q10. Central Person for a Tag
/*
  :param {
    tag: 'Franz_Liszt',
    date: '2010-08-22T04:00:00.000+0000'
  }
*/
MATCH (person:Person), (tag:Tag)
OPTIONAL MATCH (person)-[i:hasInterest]->(tag)
OPTIONAL MATCH (person)<-[c:hasCreator]-(message:Message)-[:hasTag]->(tag)
WITH person, CASE i WHEN null THEN 0 ELSE 100 END + count(message) AS score
// TODO ADD friendsScore
RETURN
  person.id,
  score
ORDER BY
  score DESC,
  person.id ASC
LIMIT 100
