// Q5. Top posters in a country
// :param country
MATCH
  (:Country {name: $country})<-[:isPartOf]-(:City)<-[:isLocatedIn]-
  (person:Person)<-[:hasMember]-(forum:Forum)
WITH forum, count(person) AS numberOfMembers
ORDER BY numberOfMembers DESC
LIMIT 100
MATCH (forum)-[:hasMember]->(person:Person)<-[:hasCreator]-(post:Post)
RETURN
  person.id,
  person.firstName,
  person.lastName,
  person.creationDate,
  count(post) AS postCount
ORDER BY
  postCount DESC,
  person.id ASC
LIMIT 100
