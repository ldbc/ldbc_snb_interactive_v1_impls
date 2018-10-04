MATCH (person:Person {id:$personId})-[:KNOWS*2..2]-(friend:Person)-[:IS_LOCATED_IN]->(city:Place)
WHERE 
  ((friend.birthday/100%100 = $month AND friend.birthday%100 >= 21) OR
  (friend.birthday/100%100 = $nextMonth AND friend.birthday%100 < 22))
  AND not(friend=person)
  AND not((friend)-[:KNOWS]-(person))
WITH DISTINCT friend, city, person
OPTIONAL MATCH (friend)<-[:HAS_CREATOR]-(post:Post)
WITH friend, city, collect(post)+[null] AS posts, count(post) AS postCount, person
UNWIND posts AS commonPostCandidate
WITH
  friend,
  city,
  commonPostCandidate,
  postCount,
  person
WHERE (commonPostCandidate)-[:HAS_TAG]->(:Tag)<-[:HAS_INTEREST]-(person) OR commonPostCandidate IS NULL
WITH
  friend,
  city,
  postCount,
  count(commonPostCandidate) AS commonPostCount
RETURN
  friend.id AS personId,
  friend.firstName AS personFirstName,
  friend.lastName AS personLastName,
  commonPostCount - (postCount - commonPostCount) AS commonInterestScore,
  commonPostCount,
  postCount,
  friend.gender AS personGender,
  city.name AS personCityName
ORDER BY commonInterestScore DESC, toInteger(personId) ASC
LIMIT 10
