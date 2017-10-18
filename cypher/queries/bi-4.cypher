// Q4. Popular topics in a country
// :param tagClass
// :param country
MATCH (:Country {name: $country})<-[:isPartOf]-(:City)<-[:isLocatedIn]-
  (person:Person)<-[:hasModerator]-(forum:Forum)-[:containerOf]->
  (post:Post)-[:hasTag]->(:Tag)-[:hasType]->(:TagClass {name: $tagClass})
RETURN
  forum.id,
  forum.title,
  forum.creationDate,
  person.id,
  count(post) AS postCount
ORDER BY
  postCount DESC,
  forum.id ASC
LIMIT 20
