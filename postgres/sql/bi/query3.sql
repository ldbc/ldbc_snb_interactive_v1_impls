-- Tag mm to mm, restrict by country, demographic
--  - Month (Needs posts per month)
select
  coalesce(m1.t_name, m2.t_name) tag_name,
  coalesce(cnt1, 0),
  coalesce(cnt2, 0),
  abs(coalesce(cnt2, 0) - coalesce(cnt1, 0)) as diff
from
  (select t_name, count(*) as cnt1
    from post, post_tag, tag
    where t_tagid = pst_tagid
      and pst_postid = ps_postid
      and ps_creationdate >= --1--
      and ps_creationdate < --2--
    group by t_name) m1
  full outer join (select t_name, count(*) as cnt2
    from post, post_tag, tag
    where t_tagid = pst_tagid
      and pst_postid = ps_postid
      and ps_creationdate >=  --3--
      and ps_creationdate <  --4--
    group by t_name) m2 on m1.t_name = m2.t_name
order by
  diff desc,
  tag_name asc
limit 100;
