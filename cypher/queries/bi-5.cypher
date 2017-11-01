// Q5. Top posters in a country
/*
  :param { country: 'Yemen' }
*/
MATCH
  (:Country {name: $country})<-[:isPartOf]-(:City)<-[:isLocatedIn]-
  (person:Person)<-[:hasMember]-(forum:Forum)
WITH forum, count(person) AS numberOfMembers
ORDER BY numberOfMembers DESC
LIMIT 100
WITH collect(forum) AS popularForums
UNWIND popularForums AS forum
MATCH
  (forum)-[:hasMember]->(person:Person)<-[:hasCreator]-(post:Post)
  <-[:containerOf]-(popularForum:Forum)
WHERE popularForum IN popularForums
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
