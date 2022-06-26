/* IS7. Replies of a message
\set messageId 824633720985
 */
select p2.messageid, p2.content, p2.creationdate, personid, firstname, lastname,
    (case when exists (
                       select 1 from Person_knows_Person
               where p1.CreatorPersonId = person1id and p2.CreatorPersonId = person2id)
      then TRUE
      else FALSE
      end)
from message p1, message p2, person
where
  p1.messageid = :messageId and p2.replyof = p1.messageid and p2.CreatorPersonId = personid
order by p2.creationdate desc, p2.CreatorPersonId asc;
;
