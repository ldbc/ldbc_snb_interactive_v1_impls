// IS3. Friends of a person
/*
:params { personId: 10995116277794 }
*/
MATCH (n:Person {id: $personId })-[r:KNOWS]-(friend)
RETURN
    friend.id AS personId,
    friend.firstName AS firstName,
    friend.lastName AS lastName,
    r.creationDate AS friendshipCreationDate
ORDER BY
    friendshipCreationDate DESC,
    toInteger(personId) ASC
