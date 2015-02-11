select cnt, count (*) as n
from
  (select p_personid, count (ps_postid) as cnt
   from person left join post
   on ps_creatorid = p_personid and ps_creationdate > '2012-1-1'::date
   group by p_personid) post_cnt
group by cnt
order by cnt desc, n;

