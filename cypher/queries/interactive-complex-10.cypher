MATCH (person:Person {id:$personId})-[:KNOWS*2..2]-(friend:Person)-[:IS_LOCATED_IN]->(city:Place)
WHERE 
  ((friend.birthday/100%100 = $month AND friend.birthday%100 >= 21) OR
  (friend.birthday/100%100 = $nextMonth AND friend.birthday%100 < 22))
  AND not(friend=person)
  AND not((friend)-[:KNOWS]-(person))
WITH DISTINCT friend, city, person
OPTIONAL MATCH (friend)<-[:HAS_CREATOR]-(post:Post)
WITH friend, city, collect(post) AS posts, person
WITH 
  friend,
  city,
  length(posts) AS postCount,
  length([p IN posts WHERE (p)-[:HAS_TAG]->(:Tag)<-[:HAS_INTEREST]-(person)]) AS commonPostCount
RETURN
  friend.id AS personId,
  friend.firstName AS personFirstName,
  friend.lastName AS personLastName,
  commonPostCount - (postCount - commonPostCount) AS commonInterestScore,
  friend.gender AS personGender,
  city.name AS personCityName
ORDER BY commonInterestScore DESC, toInteger(personId) ASC
LIMIT 10
