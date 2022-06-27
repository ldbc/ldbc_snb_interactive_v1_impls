INSERT INTO person (
    creationDate
  , id
  , firstName
  , lastName
  , gender
  , birthday
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
  , :languages
  , :emails
);
