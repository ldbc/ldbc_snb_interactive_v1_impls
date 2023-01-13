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
  , :personFirstName::varchar
  , :personLastName::varchar
  , :gender::varchar
  , :birthday
  , :locationIP::varchar
  , :browserUsed::varchar
  , :cityId
  , :languages::varchar
  , :emails::varchar
);
