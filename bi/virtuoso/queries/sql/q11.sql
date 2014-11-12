-- Same vs new tags,  intros new tag to thread, does not get slammed, gets liked
-- Scope to forums, time
select p_personid, t_name, count (*) as cnt
from person, post org, post rep, post_tag rtag, tag, country 
where rep.ps_replyof = org.ps_postid and rtag.pst_tagid = rep.ps_postid 
      and not exists (select 1 from post_tag orgtag
      	      	     where orgtag.pst_postid = org.ps_postid and rtag.pst_tagid = orgtag.pst_tagid)
      and exists (select 1 from likes where l_postid = rep.ps_postid)
and rep.ps_creatorid = p_personid and p_placeid = ctry_city and ctry_name = 'Mexico'
and rtag.pst_tagid = t_tagid
group by p_personid, t_name 
order by cnt desc;
