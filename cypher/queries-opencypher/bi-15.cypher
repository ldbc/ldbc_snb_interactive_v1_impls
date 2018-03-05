// Q15. Social normals
/*
  :param { country: 'Burma' }
*/
MATCH
  (country:Country {name: $country})
MATCH
  (country)<-[:IS_PART_OF]-(:City)<-[:IS_LOCATED_IN]-(person1:Person)
OPTIONAL MATCH
  // start a new MATCH as friend might live in the same City
  // and thus can reuse the IS_PART_OF edge
  (country)<-[:IS_PART_OF]-(:City)<-[:IS_LOCATED_IN]-(friend1:Person),
  (person1)-[:KNOWS]-(friend1)
WITH country, person1, count(friend1) AS friend1Count
WITH country, avg(friend1Count) AS socialNormalFloat
WITH country, floor(socialNormalFloat) AS socialNormal
MATCH
  (country)<-[:IS_PART_OF]-(:City)<-[:IS_LOCATED_IN]-(person2:Person)
OPTIONAL MATCH
  (country)<-[:IS_PART_OF]-(:City)<-[:IS_LOCATED_IN]-(friend2:Person)-[:KNOWS]-(person2)
WITH country, person2, count(friend2) AS friend2Count, socialNormal
WHERE friend2Count = socialNormal
RETURN
  person2.id,
  friend2Count AS count
ORDER BY
  person2.id ASC
LIMIT 100
