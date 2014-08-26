-- Most liked 
select top 100 ps_postid, p_firstname, p_lastname, ps_creationdate, count (*) 
from post, person, likes
where ps_postid in (select l_postid, count (*) from likes group by l_postid having count (*) > 100)
      and p_personid = ps_creatorid 
      and l_postid = ps_postid
      and ps_creationdate > cast ('2010-3-1' as date)
group by ps_postid, p_firstname, p_lastname, ps_creationdate 
order by ps_creationdate desc;
