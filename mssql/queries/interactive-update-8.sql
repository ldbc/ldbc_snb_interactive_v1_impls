INSERT Person_knows_Person ($from_id, $to_id, Person1id, Person2id, creationDate)
VALUES
(
    (SELECT $NODE_ID FROM Person WHERE personId = :person1Id),
    (SELECT $NODE_ID FROM Person WHERE personId = :person2Id),
    :person1Id,
    :person2Id,
    :creationDate
),
(
    (SELECT $NODE_ID FROM Person WHERE personId = :person2Id),
    (SELECT $NODE_ID FROM Person WHERE personId = :person1Id),
    :person2Id,
    :person1Id,
    :creationDate
);
