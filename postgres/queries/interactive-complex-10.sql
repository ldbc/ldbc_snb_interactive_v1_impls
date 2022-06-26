/* Q10. Friend recommendation
\set personId 17592186044461
\set month 5
 */
select person.id, firstname, lastname,
       ( select count(distinct Message.messageid)
         from message, Message_hasTag_Tag pt1
         where CreatorPersonId = Person.id
           and ParentMessageId IS NULL -- post, not comment
           and Message.messageid = pt1.messageid
           and exists (select * from Person_hasInterest_Tag where Person.id = :personId and Person_hasInterest_Tag.tagid = pt1.tagid)
       ) -
       ( select count(*)
         from message
         where CreatorPersonId = Person.id
           and ParentMessageId IS NULL -- post, not comment
           and not exists (select * from Person_hasInterest_Tag, Message_hasTag_Tag where Person.id = :personId and Person_hasInterest_Tag.tagid = Message_hasTag_Tag.tagid and Message_hasTag_Tag.messageid = Message.messageid)
       ) as score,
       gender, City.name
from person, City,
 ( select distinct k2.person2id
   from Person_knows_Person k1, Person_knows_Person k2
   where k1.person1id = :personId
     and k1.person2id = k2.person1id
     and k2.person2id <> :personId
     and not exists (select * from Person_knows_Person where person1id = :personId and person2id = k2.person2id)
 ) f
where LocationCityId = City.id
  and person.id = f.person2id
  and ((extract(month from birthday) = :month and (case when extract(day from birthday) >= 21 then true else false end))
    or (extract(month from birthday) = :month % 12 + 1 and (case when extract(day from birthday) < 22 then true else false end))
  )
order by score desc, person.id
limit 10
;
