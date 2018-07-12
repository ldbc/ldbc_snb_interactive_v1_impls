with recursive cposts(ps_postid, ps_content, ps_imagefile, ps_creationdate, ps_replyof, ps_creatorid) AS (
	  select ps_postid, ps_content, ps_imagefile, ps_creationdate, ps_replyof, ps_creatorid
	  from post
	  where ps_creatorid = :personId
	  order by ps_creationdate desc
	  limit 10
), parent(postid,replyof,orig_postid,creator) AS (
	  select ps_postid, ps_replyof, ps_postid, ps_creatorid from cposts
	UNION ALL
	  select ps_postid, ps_replyof, orig_postid, ps_creatorid
      from post,parent
      where ps_postid=replyof
)
select p1.ps_postid, COALESCE(ps_imagefile,'')||COALESCE(ps_content,''), p1.ps_creationdate,
       p2.ps_postid, p2.p_personid, p2.p_firstname, p2.p_lastname
from 
     (select ps_postid, ps_content, ps_imagefile, ps_creationdate, ps_replyof from cposts
     ) p1
     left outer join
     (select orig_postid, postid as ps_postid, p_personid, p_firstname, p_lastname
      from parent, person
      where replyof is null and creator = p_personid
     )p2  
     on p2.orig_postid = p1.ps_postid
      order by ps_creationdate desc, p2.ps_postid desc;
