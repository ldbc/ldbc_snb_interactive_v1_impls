/* Q9. Recent messages by friends or friends of friends
\set personId 17592186044461
\set maxDate '\'2010-11-16\''::date
 */
select person.id, firstname, lastname, messageid, COALESCE(imagefile, content), message.creationdate
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
  ) f, person, message
where person.id = CreatorPersonId
  and person.id = f.person2id
  and message.creationdate < :maxDate
order by message.creationdate desc, messageid asc
limit 20
;
