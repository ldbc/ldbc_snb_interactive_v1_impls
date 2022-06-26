/* Q7. Recent likers
\set personId 17592186044461
 */
select Person.id, firstname, lastname, l.creationdate, Message.MessageId,
    COALESCE(imagefile, content),
    CAST(floor(EXTRACT(EPOCH FROM (l.creationdate - Message.creationdate))) AS INTEGER) / 60 as minutesLatency,
    (case when exists (select 1 from Person_knows_Person where person1id = :personId and person2id = Person.id) then 0 else 1 end) as isnew
from
  (select PersonId, max(Person_likes_Message.creationdate) as creationdate
   from Person_likes_Message, Message
   where
     Person_likes_Message.MessageId = Message.MessageId and
     CreatorPersonId = :personId
   group by PersonId
   order by creationdate desc
   limit 20
  ) tmp, message, person, Person_likes_Message as l
where
    Person.id = tmp.PersonId and
    tmp.PersonId = l.PersonId and
    tmp.creationdate = l.creationdate and
    l.MessageId = Message.MessageId
order by creationdate desc, Person.id asc
;
