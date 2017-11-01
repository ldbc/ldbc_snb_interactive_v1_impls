// Q16. Experts in social circle
/*
  :param {
    person: '',
    tagClass: '',
    minPathDistance: ,
    maxPathDistance:
  }
*/
MATCH (person:Person)-[:knows*1..2]-(:Person)
RETURN person.id
TODO
