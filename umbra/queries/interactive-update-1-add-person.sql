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
