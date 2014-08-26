-- Tag mm to mm, restrict by country, demographic
select top 100 m1.t_name, cnt1, cnt2, cnt2 - cnt1 as diff
from
	(select t_name, count (*) as cnt1 
		from post, post_tag, tag 
		where t_tagid = pst_tagid and pst_postid = ps_postid
		and ps_creationdate between cast ('2012-6-1' as date) and dateadd ('month', 1, cast ('2012-6-1' as date))
		group by t_name) m1,
	(select t_name, count (*) as cnt2 
		from post, post_tag, tag 
		where t_tagid = pst_tagid and pst_postid = ps_postid
		and ps_creationdate between dateadd ('month', 1, cast ('2012-6-1' as date)) and dateadd ('month', 2, cast ('2012-6-1' as date))
		group by t_name) m2
where m1.t_name = m2.t_name
order by diff desc;

