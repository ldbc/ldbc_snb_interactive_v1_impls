INSERT INTO person (
    id
  , firstName
  , lastName
  , gender
  , birthday
  , creationDate
  , locationIP
  , browserUsed
  , LocationCityId
  , speaks
  , email
)
VALUES
(
    :personId
  , :personFirstName
  , :personLastName
  , :gender
  , :birthday
  , :creationDate
  , :locationIP
  , :browserUsed
  , :cityId
  , unnest(:languages)
  , unnest(:emails)
);
