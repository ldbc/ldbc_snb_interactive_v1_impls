/* Q3. Friends and friends of friends that have been to given countries
\set personId 17592186044461
\set countryXName '\'Angola\''
\set countryYName '\'Colombia\''
\set startDate '\'2010-06-01\''::date
\set durationDays 28
 */
select
  person.id,
  person.firstname,
  person.lastname,
  ct1,
  ct2,
  total as totalcount
from
 ( select person2id
   from Person_knows_Person
   where person1id = :personId
   union
   select k2.person2id
   from Person_knows_Person k1, Person_knows_Person k2
   where k1.person1id = :personId
     and k1.person2id = k2.person1id
     and k2.person2id <> :personId
 ) f,
 person,
 City,
 Country,
 (
  select chn.CreatorPersonId, ct1, ct2, ct1 + ct2 as total
  from
   (
      select CreatorPersonId as CreatorPersonId, count(*) as ct1 from Message, Country
      where LocationCountryId = Country.id
        and Country.name = :countryXName
        and Message.creationdate >= :startDate
        and Message.creationdate < (:startDate + INTERVAL '1 days' * :durationDays)
      group by CreatorPersonId
   ) chn,
   (
      select CreatorPersonId as CreatorPersonId, count(*) as ct2 from Message, Country
      where LocationCountryId = Country.id
        and Country.name = :countryYName
        and Message.creationdate >= :startDate
        and Message.creationdate < (:startDate + INTERVAL '1 days' * :durationDays)
      group by CreatorPersonId
   ) ind
  where chn.CreatorPersonId = IND.CreatorPersonId
 ) cpc
where f.person2id = Person.id
  and Person.LocationCityId = City.id
  and City.PartOfCountryId = Country.id
  and Country.name <> :countryXName
  and Country.name <> :countryYName
  and f.person2id = cpc.CreatorPersonId
order by totalcount desc, person.id asc
limit 20
;
