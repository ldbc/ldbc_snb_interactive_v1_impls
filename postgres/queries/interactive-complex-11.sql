/* Q11. Job referral
\set personId 17592186044461
\set countryName '\'Hungary\''
\set workFromYear 2011
 */
select person.id, firstname, lastname, Company.name, Person_workAt_Company.workFrom
from Person, Person_workAt_Company, Company, Country,
 ( select person2id
   from Person_knows_Person
   where person1id = :personId
   union
   select k2.person2id
   from Person_knows_Person k1, Person_knows_Person k2
   where k1.person1id = :personId
     and k1.person2id = k2.person1id
     and k2.person2id <> :personId
 ) f
where
    person.id = f.person2id and
    person.id = Person_workAt_Company.PersonId and
    Person_workAt_Company.CompanyId = Company.id and
    Person_workAt_Company.workfrom < :workFromYear and
    Country.id = Company.LocationPlaceId and
    Country.name = :countryName
order by Person_workAt_Company.workFrom, person.id, Company.name desc
limit 10
;
