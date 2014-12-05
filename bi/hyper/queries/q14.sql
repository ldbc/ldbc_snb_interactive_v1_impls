with recursive replies (ps_replyof, ps_postid) 
as (select ps_replyof, ps_postid from post
    where ps_creationdate >= date '2011-10-01'
    and ps_creationdate <= date '2011-10-01' + interval '3' month
union all 
select r.ps_replyof, p.ps_postid from replies r, post p
where r.ps_postid=p.ps_replyof and  ps_creationdate >= date '2011-10-01'
    and ps_creationdate <= date '2011-10-01' + interval '3' month
)
select top 100 p_personid, p_firstname, p_lastname,
       count (*) as cnt, count (distinct org.ps_postid) as n_threads
from person, post org, replies
where replies.ps_replyof = org.ps_postid and org.ps_replyof is null
      and org.ps_creatorid = p_personid 
      and org.ps_creationdate >= date '2011-10-01' 
      and org.ps_creationdate <= date '2011-10-01' +interval '3' month
group by p_personid, p_firstname, p_lastname
order by cnt desc, p_personid;

