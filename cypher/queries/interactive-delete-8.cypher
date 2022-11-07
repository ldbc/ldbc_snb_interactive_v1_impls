OPTIONAL MATCH (:Person {id: $person1Id})-[knows:KNOWS]-(:Person {id: $person2Id})
WITH 1/count(knows) AS testWhetherKnowsFound
// DEL 8: Remove friendship
MATCH (:Person {id: $person1Id})-[knows:KNOWS]-(:Person {id: $person2Id})
DELETE knows
RETURN count(*)
