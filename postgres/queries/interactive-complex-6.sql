/* Q6. Tag co-occurrence
\set personId 17592186044461
\set tagName '\'Carl_Gustaf_Emil_Mannerheim\''
 */
select name, count(*) as postcount
from Tag, Message_hasTag_Tag, Message,
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
where CreatorPersonId = f.person2id
  and ParentMessageId IS NULL -- post, not comment
  and Message.MessageId = Message_hasTag_Tag.MessageId
  and Message_hasTag_Tag.TagId = Tag.id
  and name <> :tagName
  and exists (select * from Tag, Message_hasTag_Tag where Message.MessageId = Message_hasTag_Tag.MessageId and Message_hasTag_Tag.TagId = Tag.id and Tag.name = :tagName)
group by name
order by postCount desc, name asc
limit 10
;
