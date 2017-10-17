// Experts in social circle
MATCH (person:Person)-[:knows*1..2]-(:Person)
RETURN person.id
