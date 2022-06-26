/* Q1. Transitive friends with certain name
\set personId 17592186044461
\set firstName '\'Jose\''
 */
select
  person.id,
  person.lastname,
  min(dist) as dist,
  person.birthday,
  person.creationdate,
  person.gender,
  person.browserused,
  person.locationip,
  --(select array_agg(pe_email) from person_email where pe_personid = id group by pe_personid) as emails,
  --(select array_agg(plang_language) from person_language where plang_personid = id group by plang_personid) as languages,
  person.email AS emails,
  person.speaks AS languages,
  City.name,
  (select array_agg(ARRAY[u.name, Person_studyAt_University.classYear::text, c.name]) from Person_studyAt_University, University u, City c where Person_studyAt_University.PersonId = Person.id and Person_studyAt_University.UniversityId = u.id and u.LocationPlaceId = c.id group by Person.id) as university,
  (select array_agg(ARRAY[comp.name, Person_workAt_Company.workFrom::text, ctr.name]) from Person_workAt_Company, Company comp, Country ctr where Person_workAt_Company.PersonId = Person.id and Person_workAt_Company.CompanyId = comp.id and comp.LocationPlaceId = ctr.id group by Person.id) as company
from
    (
    select person2id as id, 1 as dist from Person_knows_Person, person
    where person1id = :personId
      and person.id = person2id
      and firstname = :firstName
    union all
    select b.person2id as id, 2 as dist from Person_knows_Person a, Person_knows_Person b, person
    where a.person1id = :personId
      and b.person1id = a.person2id
      and person.id = b.person2id
      and firstname = :firstName
      and person.id != :personId -- excluding start person
    union all
    select c.person2id as id, 3 as dist from Person_knows_Person a, Person_knows_Person b, Person_knows_Person c, person
    where a.person1id = :personId
      and b.person1id = a.person2id
      and b.person2id = c.person1id
      and person.id = c.person2id
      and firstname = :firstName
      and person.id != :personId -- excluding start person
    ) tmp, person, City
  where
    person.id = tmp.id and
    person.LocationCityId = City.id
  group by person.id, lastname, birthday, creationdate, gender, browserused, locationip, City.name
  order by dist, lastname, person.id
  LIMIT 20
;
