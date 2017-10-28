select
  ps_postid,
  p_firstname,
  p_lastname,
  ps_creationdate,
  count(*)
from post, person, likes
where ps_postid in (select l_postid from likes group by l_postid having count(*) > --2--)
  and p_personid = ps_creatorid
  and l_postid = ps_postid
  and ps_creationdate > --1--
group by ps_postid, p_firstname, p_lastname, ps_creationdate
order by
  ps_creationdate desc,
  ps_postid asc
limit 100;
