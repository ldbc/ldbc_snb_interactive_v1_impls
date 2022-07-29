// Q13. Single shortest path
/*
:param [{ person1Id, person2Id }] => { RETURN
  8796093022390 AS person1Id,
  8796093022357 AS person2Id
}
*/
OPTIONAL MATCH // the 'OPTIONAL' modifier can be deleted if the parameter curation ensures that both person1 and person2 exist
    (person1:Person {id: $person1Id}),
    (person2:Person {id: $person2Id})
OPTIONAL MATCH
    path = shortestPath((person1)-[:KNOWS*]-(person2))
RETURN
    CASE path IS NULL
        WHEN true THEN -1
        ELSE length(path)
    END AS shortestPathLength
