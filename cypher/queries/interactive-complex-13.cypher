// Q13. Single shortest path
/*
:params { person1Id: 8796093022390, person2Id: 8796093022357 }
*/
MATCH
    (person1:Person {id: $person1Id}),
    (person2:Person {id: $person2Id}),
    path = shortestPath((person1)-[:KNOWS*]-(person2))
RETURN
    CASE path IS NULL
        WHEN true THEN -1
        ELSE length(path)
    END AS shortestPathLength