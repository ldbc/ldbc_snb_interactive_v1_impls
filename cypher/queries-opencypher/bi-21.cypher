// Q21. Zombies in a country
/*
  :param {
    country: 'Ethiopia',
    endDate: 20130101000000000
  }
*/
MATCH (country:Country {name: $country})
WITH
  country,
  $endDate/10000000000000   AS endDateYear,
  $endDate/100000000000%100 AS endDateMonth
MATCH
  (country)<-[:IS_PART_OF]-(:City)<-[:IS_LOCATED_IN]-(zombie:Person)
OPTIONAL MATCH
  (zombie)<-[:HAS_CREATOR]-(message:Message)
WHERE zombie.creationDate  < $endDate
  AND message.creationDate < $endDate
WITH
  country,
  zombie,
  endDateYear,
  endDateMonth,
  zombie.creationDate/10000000000000   AS zombieCreationYear,
  zombie.creationDate/100000000000%100 AS zombieCreationMonth,
  count(message) AS messageCount
WITH
  country,
  zombie,
  12 * (endDateYear  - zombieCreationYear )
     + (endDateMonth - zombieCreationMonth)
     + 1 AS months,
  messageCount
WHERE messageCount / months < 1
WITH
  country,
  collect(zombie) AS zombies
UNWIND zombies AS zombie
OPTIONAL MATCH
  (zombie)<-[:HAS_CREATOR]-(message:Message)<-[:LIKES]-(likerZombie:Person)
WHERE likerZombie IN zombies
WITH
  zombie,
  count(likerZombie) AS zombieLikeCount
OPTIONAL MATCH
  (zombie)<-[:HAS_CREATOR]-(message:Message)<-[:LIKES]-(likerPerson:Person)
WHERE likerPerson.creationDate < $endDate
WITH
  zombie,
  zombieLikeCount,
  count(likerPerson) AS totalLikeCount
RETURN
  zombie.id,
  zombieLikeCount,
  totalLikeCount,
  CASE totalLikeCount
    WHEN 0 THEN 0
    ELSE zombieLikeCount / toFloat(totalLikeCount)
  END AS zombieScore
ORDER BY
  zombieScore DESC,
  zombie.id ASC
LIMIT 100
