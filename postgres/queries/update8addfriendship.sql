insert into knows (
    k_person1id
  , k_person2id
  , k_creationdate
)
values
(
    :person1Id
  , :person2Id
  , :creationDate
),
(
    :person2Id
  , :person1Id
  , :creationDate
);
