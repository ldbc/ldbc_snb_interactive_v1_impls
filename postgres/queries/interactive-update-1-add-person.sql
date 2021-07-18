insert into person (
    p_personid
  , p_firstname
  , p_lastname
  , p_gender
  , p_birthday
  , p_creationdate
  , p_locationip
  , p_browserused
  , p_placeid
)
values
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
);
