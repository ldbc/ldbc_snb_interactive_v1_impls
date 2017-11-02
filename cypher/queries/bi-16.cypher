// Q16. Experts in social circle
/*
  :param {
    personId: 6597069777419,
    country: 'Pakistan',
    tagClass: 'MusicalArtist',
    minPathDistance: 3,
    maxPathDistance: 5
  }
*/
// This query will not work in a browser as is. I tried alternatives approaches,
// e.g. enabling path of arbitrary lengths, saving the path to a variable p and
// checking for `$minPathDistance <= length(p)`, but these could not be
// evaluated due to the excessive amount of paths.
// If you would like to test the query in the browser, replace the values of
// $minPathDistance and $maxPathDistance to a constant.
MATCH
  (:Person {id: $personId})-[:knows*$minPathDistance..$maxPathDistance]-
  (person:Person)-[:isLocatedIn]->(:City)-[:isPartOf]->
  (:Country {name: $country}),
  (person)<-[:hasCreator]-(message:Message)-[:hasTag]->(:Tag)-[:hasType]->
  (:TagClass {name: $tagClass})
MATCH (message)-[:hasTag]->(tag:Tag)
RETURN
  person.id,
  tag.name,
  count(message) AS messageCount
ORDER BY
  messageCount DESC,
  tag.name ASC,
  person.id ASC
LIMIT 100
