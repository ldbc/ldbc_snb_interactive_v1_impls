// Q15. Social normals
/*
  :param { country: 'Spain' }
*/
MATCH
  (country:Country {name: $country})
MATCH
  (country)<-[:IS_PART_OF]-(:City)<-[:IS_LOCATED_IN]-(person:Person)
MATCH
  // start a new MATCH as friend might live in the same City
  // and thus can reuse the IS_PART_OF edge
  (country)<-[:IS_PART_OF]-(:City)<-[:IS_LOCATED_IN]-(friend:Person),
  (person)-[:KNOWS]-(friend)
WITH country, person, count(friend) AS friendCount
WITH country, floor(avg(friendCount)) as socialNormal
MATCH
  (country)<-[:IS_PART_OF]-(:City)<-[:IS_LOCATED_IN]-(person:Person)
MATCH
  (country)<-[:IS_PART_OF]-(:City)<-[:IS_LOCATED_IN]-(friend:Person),
  (person)-[:KNOWS]-(friend)
WITH country, person, count(friend) AS friendCount, socialNormal
WHERE friendCount = socialNormal
RETURN
  person.id,
  friendCount AS count
ORDER BY
  person.id ASC
LIMIT 100
