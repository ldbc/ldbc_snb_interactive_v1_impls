select t_name, count(*) --Q4
from tag, post, post_tag recent, knows
where
    ps_postid = pst_postid and
    pst_tagid = t_tagid and
    ps_p_creatorid = k_person2id and
    k_person1id = --1-- and
    ps_creationdate>= --2--::timestamp and  ps_creationdate < (--2--::timestamp + INTERVAL '1 days'*--3--) and
    not exists (
        select * from 
  (select distinct pst_tagid from post, post_tag, knows
        where
	k_person1id = --1-- and
        k_person2id = ps_p_creatorid and
        pst_postid = ps_postid and
        ps_creationdate < --2--::timestamp) tags
  where  tags.pst_tagid = recent.pst_tagid)
group by t_name
order by 2 desc, t_name
limit 10