select p_personid, t_name, count (likes.l_personid) as likescnt, count(distinct rep.ps_postid) as repcnt
from person, post org, post rep, post_tag rtag, tag, country, likes
where rep.ps_replyof = org.ps_postid and rtag.pst_postid = rep.ps_postid 
      and not exists (select 1 from post_tag orgtag
      	      	     where orgtag.pst_postid = org.ps_postid and rtag.pst_tagid = orgtag.pst_tagid)
      and exists (select 1 from likes where l_postid = rep.ps_postid)
      and rep.ps_content not like '%OK%'
and likes.l_postid = rep.ps_postid 
and rep.ps_creatorid = p_personid and p_placeid = ctry_city and ctry_name = 'India'
and rtag.pst_tagid = t_tagid
group by p_personid, t_name 
order by likescnt + repcnt desc, p_personid asc 
limit 100;

