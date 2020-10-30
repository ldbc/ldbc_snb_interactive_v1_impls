insert into person (
    p_creationdate
  , p_personid
  , p_firstname
  , p_lastname
  , p_gender
  , p_birthday
  , p_locationip
  , p_browserused
  , p_placeid
)
values
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
);
