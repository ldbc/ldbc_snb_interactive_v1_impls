// Q15. Social normals
/*
  :param { country: 'Spain' }
*/
MATCH
  (country:Country {name: $country})
MATCH
  (country)<-[:isPartOf]-(:City)<-[:isLocatedIn]-(person:Person)
MATCH
  // start a new MATCH as friend might live in the same City
  // and thus can reuse the isPartOf edge
  (country)<-[:isPartOf]-(:City)<-[:isLocatedIn]-(friend:Person),
  (person)-[:knows]-(friend)
WITH country, person, count(friend) AS friendCount
WITH country, floor(avg(friendCount)) as socialNormal
MATCH
  (country)<-[:isPartOf]-(:City)<-[:isLocatedIn]-(person:Person)
MATCH
  (country)<-[:isPartOf]-(:City)<-[:isLocatedIn]-(friend:Person),
  (person)-[:knows]-(friend)
WITH country, person, count(friend) AS friendCount, socialNormal
WHERE friendCount = socialNormal
RETURN
  person.id,
  friendCount AS count
ORDER BY
  person.id ASC
LIMIT 100
