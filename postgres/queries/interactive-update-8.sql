insert into knows (
    k_creationdate
  , k_person1id
  , k_person2id
)
values
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
