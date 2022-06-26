// DEL 8: Remove friendship
MATCH (:Person {id: $person1Id})-[knows:KNOWS]-(:Person {id: $person2Id})
DELETE knows
RETURN count(*)
