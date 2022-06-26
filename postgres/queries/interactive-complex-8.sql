/* Q8. Recent replies
\set personId 17592186044461
 */
select p1.CreatorPersonId, firstname, lastname, p1.creationdate, p1.messageid, p1.content
  from message p1, message p2, Person
  where
      p1.ParentMessageId = p2.MessageId and
      p2.CreatorPersonId = :personId and
      Person.Id = p1.CreatorPersonId
order by p1.creationDate desc, p1.MessageId asc
limit 20
;
