// Central Person for a Tag
MATCH (person:Person), (tag:Tag)
OPTIONAL MATCH (person)-[i:hasInterest]->(tag)
OPTIONAL MATCH (person)<-[c:hasCreator]-(message:Message)-[:hasTag]->(tag)
WITH person, CASE i WHEN null THEN 0 ELSE 100 END + count(message) AS score
// TODO ADD friendsScore
RETURN person.id, score
ORDER BY score DESC, person.id ASC
LIMIT 100
