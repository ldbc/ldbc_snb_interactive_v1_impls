with recursive extended_tags(s_subtagclassid,s_supertagclassid) as (
	(select s_supertagclassid,s_supertagclassid from subclass
	UNION select s_subtagclassid,s_subtagclassid from subclass
	)
	UNION
	select s.s_subtagclassid,t.s_supertagclassid from subclass s, extended_tags t
		where s.s_supertagclassid=t.s_subtagclassid
)
select p_personid, p_firstname, p_lastname, array_agg(distinct t_name), count(*) --Q12
from person, post p1, knows, post p2, post_tag, 
	(select distinct t_tagid, t_name from tag, tag_tagclass where t_tagid=ttc_tagid and (ttc_tagclassid in (
  		select distinct s_subtagclassid from extended_tags k, tagclass
		where tc_tagclassid = k.s_supertagclassid and tc_name = '--2--') 
   )) selected_tags
where
  k_person1id = --1-- and 
  k_person2id = p_personid and 
  p_personid = p1.ps_creatorid and 
  p1.ps_replyof = p2.ps_postid and 
  p2.ps_replyof is null and
  p2.ps_postid = pst_postid and 
  pst_tagid = t_tagid
group by p_personid, p_firstname, p_lastname
order by 5 desc, 1
limit 20
