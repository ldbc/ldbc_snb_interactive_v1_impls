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
  (:Person {id: $personId})-[:KNOWS*$minPathDistance..$maxPathDistance]-(person:Person)
WITH DISTINCT person
MATCH
  (person)-[:IS_LOCATED_IN]->(:City)-[:IS_PART_OF]->(:Country {name: $country}),
  (person)<-[:HAS_CREATOR]-(message:Message)-[:HAS_TAG]->(:Tag)-[:HAS_TYPE]->
  (:TagClass {name: $tagClass})
MATCH
  (message)-[:HAS_TAG]->(tag:Tag)
RETURN
  person.id,
  tag.name,
  count(DISTINCT message) AS messageCount
ORDER BY
  messageCount DESC,
  tag.name ASC,
  person.id ASC
LIMIT 100
