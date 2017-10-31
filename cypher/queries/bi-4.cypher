// Q4. Popular topics in a country
/*
  :param {
    tagClass: 'MusicalArtist',
    country: 'Burma'
  }
*/
MATCH
  (:Country {name: $country})<-[:isPartOf]-(:City)<-[:isLocatedIn]-
  (person:Person)<-[:hasModerator]-(forum:Forum)-[:containerOf]->
  (post:Post)-[:hasTag]->(:Tag)-[:hasType]->(:TagClass {name: $tagClass})
RETURN
  forum.id,
  forum.title,
  forum.creationDate,
  person.id,
  count(DISTINCT post) AS postCount
ORDER BY
  postCount DESC,
  forum.id ASC
LIMIT 20