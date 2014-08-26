-- How many persons have a given number of posts  (TPCH Q13)
select cnt, count (*) as n
from
	(select p_personid, count (ps_postid) as cnt
	 from person left join post
	 on ps_creatorid = p_personid and ps_creationdate > cast ('2012-1-1' as date)
	 group by p_personid) post_cnt
group by cnt
order by cnt desc, n;
