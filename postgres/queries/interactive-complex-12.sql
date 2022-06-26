/* Q12. Expert search
\set personId 17592186044461
\set tagClassName '\'Monarch\''
 */
with recursive extended_tags(s_subtagclassid, s_supertagclassid) as (
    select id, id from tagclass
    UNION
    select tc.id, t.s_supertagclassid from tagclass tc, extended_tags t
        where tc.SubclassOfTagClassId = t.s_subtagclassid
)
select person.id, firstname, lastname, array_agg(distinct name), count(*) as replyCount
from person, message p1, Person_knows_Person, message p2, Message_hasTag_Tag, 
    (select distinct id, name from tag where (TypeTagClassId in (
          select distinct s_subtagclassid from extended_tags k, tagclass
        where id = k.s_supertagclassid and name = :tagClassName) 
   )) selected_tags
where
  person1id = :personId and 
  person2id = person.id and 
  person.id = p1.CreatorPersonId and 
  p1.ParentMessageId = p2.messageid and 
  p2.ParentMessageId is null and
  p2.messageid = Message_hasTag_Tag.messageid and 
  Message_hasTag_Tag.TagId = selected_tags.id
group by person.id, person.firstname, person.lastname
order by replyCount desc, person.id asc
limit 20
;
