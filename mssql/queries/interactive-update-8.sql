INSERT Person_knows_Person ($from_id, $to_id, Person1id, Person2id, k_creationDate)
VALUES
(
    NODE_ID_FROM_PARTS(object_id('Person'), :person1Id),
    NODE_ID_FROM_PARTS(object_id('Person'), :person2Id),
    :person1Id,
    :person2Id
    :creationDate
),
(
    NODE_ID_FROM_PARTS(object_id('Person'), :person2Id),
    NODE_ID_FROM_PARTS(object_id('Person'), :person1Id),
    :person2Id,
    :person1Id
    :creationDate
);
