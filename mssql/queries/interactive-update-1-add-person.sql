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
  , N:personFirstName
  , N:personLastName
  , :gender
  , :birthday
  , :locationIP
  , :browserUsed
  , :cityId
  , :languages
  , N:emails
);
