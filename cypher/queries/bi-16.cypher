// Q16. Experts in social circle
/*
  :param {
    personId: 6597069777419,
    country: Pakistan
    tagClass: 'MusicalArtist',
    minPathDistance: 3,
    maxPathDistance: 5
  }
*/
// This is not an efficient solution, i.e. will not work for any large data set
// only using for simplicity. Added a constant for testing.
MATCH p=(person:Person)-[:knows*5]-(:Person)
WHERE $minPathDistance <= length(p)
  AND $maxPathDistance >= length(p)
RETURN person.id
//TODO
