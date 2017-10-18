// Q16. Experts in social circle
// :param person
// :param tagClass
// :param minPathDistance
// :param maxPathDistance
MATCH (person:Person)-[:knows*1..2]-(:Person)
RETURN person.id
TODO
