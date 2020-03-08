// Q10. Central Person for a Tag
/*
  :param [{tag, date}] => { RETURN 'John_Rhys-Davies' AS tag, datetime('2012-01-22') AS date }
*/
MATCH (tag:Tag {name: $tag})
// score
OPTIONAL MATCH (tag)<-[interest:HAS_INTEREST]-(person:Person)
WITH tag, collect(person) AS interestedPersons
OPTIONAL MATCH (tag)<-[:HAS_TAG]-(message:Message)-[:HAS_CREATOR]->(person:Person)
         WHERE message.creationDate > $date
WITH tag, interestedPersons, collect(person) AS persons
WITH tag, interestedPersons + persons AS persons
UNWIND persons AS person
// poor man's disjunct union (should be changed to UNION + post-union processing in the future)
WITH DISTINCT tag, person
OPTIONAL MATCH (tag)<-[interest:HAS_INTEREST]-(person:Person)
WITH
  tag,
  person,
  count(interest) AS score // multiple by 100 deferred to next query part
OPTIONAL MATCH (tag)<-[:HAS_TAG]-(message:Message)-[:HAS_CREATOR]->(person)
         WHERE message.creationDate > $date
WITH
  tag,
  person,
  100 * score AS score,
  count(message) AS messageCount
WITH
  tag,
  person,
  score + messageCount AS score
MATCH (person)-[:KNOWS]-(friend)
WITH
  tag,
  person,
  score,
  friend
OPTIONAL MATCH (tag)<-[interest:HAS_INTEREST]-(friend:Person)
WITH
  tag,
  person,
  score,
  friend,
  count(interest) AS friendScore // multiple by 100 deferred to next query part
OPTIONAL MATCH (tag)<-[:HAS_TAG]-(message:Message)-[:HAS_CREATOR]->(friend)
         WHERE message.creationDate > $date
WITH
  person,
  score,
  friend,
  100 * friendScore AS friendScore,
  count(message) AS messageCount
WITH
  person,
  score,
  friend,
  friendScore + messageCount AS friendScore
RETURN
  person.id,
  score,
  sum(friendScore) AS friendsScore
ORDER BY
  score + friendsScore DESC,
  person.id ASC
LIMIT 100
