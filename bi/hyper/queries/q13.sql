with histogram(yy,mm,t_name,cnt) as (
select extract(year from ps_creationdate) as yy, extract(month from ps_creationdate) as mm, t_name, count (*) as cnt 
from post join person on ps_creatorid = p_personid 
  join place pcity on pcity.pl_placeid = p_placeid
  join place ctry on ctry.pl_placeid = pcity.pl_containerplaceid 
  left join (select t_name, pst_postid from post_tag, tag where t_tagid = pst_tagid) tags on pst_postid = ps_postid
where ctry.pl_name = 'India'
group by extract(year from ps_creationdate), extract(month from ps_creationdate), t_name 
having count(*) > 1
order by yy, mm, cnt desc
), histogram_rank as (
select yy, mm, t_name, cnt, 
	(select count(h2.cnt) as rank from histogram h2 where h2.yy=h1.yy and h1.mm=h2.mm and h2.cnt >= h1.cnt) as rank
 from histogram  h1 )
select * from histogram_rank  where rank < 5 order by yy,mm,cnt desc, t_name;

