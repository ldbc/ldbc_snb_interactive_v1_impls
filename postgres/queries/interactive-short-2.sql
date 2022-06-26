/* IS2. Recent messages of a person
\set personId 17592186044461
 */
with recursive cposts(messageid, content, imagefile, creationdate, replyof, CreatorPersonId) AS (
      select messageid, content, imagefile, creationdate, replyof, CreatorPersonId
      from message
      where CreatorPersonId = :personId
      order by creationdate desc
      limit 10
), parent(postid,replyof,orig_postid,creator) AS (
      select messageid, replyof, messageid, CreatorPersonId from cposts
    UNION ALL
      select messageid, replyof, orig_postid, CreatorPersonId
      from message,parent
      where messageid=replyof
)
select p1.messageid, COALESCE(imagefile, content, ''), p1.creationdate,
       p2.messageid, p2.personid, p2.firstname, p2.lastname
from 
     (select messageid, content, imagefile, creationdate, replyof from cposts
     ) p1
left join
     (select orig_postid, postid as messageid, personid, firstname, lastname
      from parent, person
      where replyof is null and creator = personid
     )p2  
on p2.orig_postid = p1.messageid
order by creationdate desc, p2.messageid desc;
;
