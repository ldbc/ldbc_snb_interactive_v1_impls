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
    :creationDate
  , :personId
  , :personFirstName
  , :personLastName
  , :gender
  , :birthday
  , :locationIP
  , :browserUsed
  , :cityId
  , :languages
  , :emails
);
