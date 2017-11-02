// Q21. Zombies in a country
/*
  :param {
    country: 'Spain',
    endDate: '2013-01-01T05:00:00.000+0000'
  }
*/
MATCH (country:Country {name: $country})
WITH
  country,
  $endDate AS endDate,
  toInteger(substring($endDate, 0, 4)) AS endDateYear,
  toInteger(substring($endDate, 5, 2)) AS endDateMonth
MATCH
  (country)<-[:isPartOf]-(:City)<-[:isLocatedIn]-
  (person:Person)<-[:hasCreator]-(message:Message)
WHERE person.creationDate < $endDate
  AND message.creationDate < $endDate
WITH
  country,
  person,
  endDateYear,
  endDateMonth,
  toInteger(substring(message.creationDate, 0, 4)) AS personCreationYear,
  toInteger(substring(message.creationDate, 5, 2)) AS personCreationMonth,
  count(message) AS messageCount
WITH
  country,
  person,
  (endDateYear  - personCreationYear ) * 12 +
  (endDateMonth - personCreationMonth) AS months,
  messageCount
WHERE messageCount / months < 1
WITH
  country,
  collect(person) AS zombies
MATCH
  (country)<-[:isPartOf]-(:City)<-[:isLocatedIn]-
  (person:Person)<-[:hasCreator]-(message:Message)<-[:likes]-(fan:Person)
WHERE fan.creationDate < $endDate
WITH
  zombies,
  person,
  collect(fan) AS fans
WITH
  person,
  size([f IN fans WHERE f in zombies]) AS zombieLikeCount,
  toFloat(size(fans)) AS totalLikeCount
RETURN
  person.id,
  zombieLikeCount,
  totalLikeCount,
  zombieLikeCount / totalLikeCount AS zombieScore
ORDER BY
  zombieScore DESC,
  person.ID ASC
LIMIT 100
