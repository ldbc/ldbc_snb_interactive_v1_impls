INSERT INTO person (
    creationDate
  , personId
  , firstName
  , lastName
  , gender
  , birthday
  , locationIP
  , browserUsed
  , LocationCityId
  , language
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
