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
MATCH p=(person:Person)-[:knows*1..2]-(:Person)
// not an efficient solution, only using for simplicity
WHERE $minPathDistance <= length(p)
  AND $maxPathDistance >= length(p)
RETURN person.id
TODO
