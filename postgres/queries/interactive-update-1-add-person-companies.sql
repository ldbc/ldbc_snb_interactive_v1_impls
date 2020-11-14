insert into person_company (
    pc_creationdate
  , pc_personid
  , pc_organisationid
  , pc_workfrom
)
values
(
    :creationDate
  , :personId
  , :organizationId
  , :worksFromYear
);
