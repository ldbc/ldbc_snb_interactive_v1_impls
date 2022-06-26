/* Q5 New groups
\set personId 17592186044461
\set minDate '\'2010-11-01\''::date
 */
select title, count(messageid) AS postCount
from (
  select title, Forum.id AS forumid, f.person2id
  from Forum, Forum_hasMember_Person,
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
  where Forum.id = Forum_hasMember_Person.ForumId
    and personid = f.person2id
    and Forum_hasMember_Person.creationDate >= :minDate
) tmp
left join message
  on tmp.forumid = Message.ContainerForumId
 and CreatorPersonId = tmp.person2id
group by forumid, title
order by postCount desc, forumid asc
limit 20
;
