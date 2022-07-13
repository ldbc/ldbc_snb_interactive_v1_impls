INSERT INTO Person_knows_Person (
    creationDate
  , Person1id
  , Person2id
)
VALUES
(
    :creationDate
  , :person1Id
  , :person2Id
),
(
    :creationDate
  , :person2Id
  , :person1Id
);
