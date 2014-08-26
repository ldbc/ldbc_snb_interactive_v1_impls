-- Problem: Too many rows in results set, maybe we can add having clause
select year (ps_creationdate) as yy, month (ps_creationdate) as mm, t_name, count (*) as cnt 
from post join person on ps_creatorid = p_personid 
  join place pcity on pcity.pl_placeid = p_placeid
  join place ctry on ctry.pl_placeid = pcity.pl_containerplaceid 
  left join (select t_name, pst_postid from post_tag, tag where t_tagid = pst_tagid) tags on pst_postid = ps_postid
where ctry.pl_name = 'India'
group by yy, mm, t_name 
order by yy, mm, cnt desc;
