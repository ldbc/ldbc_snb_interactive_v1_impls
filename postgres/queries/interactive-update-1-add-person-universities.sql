insert into person_university (
    pu_creationdate
  , pu_personid
  , pu_organisationid
  , pu_classyear
)
values
(
    :creationDate
  , :personId
  , :organizationId
  , :studiesFromYear
);
