-- Q3. Tag evolution
-- year: 2010,
-- month: 10
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
         and extract(year  from ps_creationdate) = $year
         and extract(month from ps_creationdate) = $month
   group by t_name) m1
  full outer join (select t_name, count(*) as cnt2
                   from post, post_tag, tag
                   where t_tagid = pst_tagid
                         and pst_postid = ps_postid
                         and extract(year  from ps_creationdate) = $year + $month / 12
                         and extract(month from ps_creationdate) = $month % 12 + 1
                   group by t_name) m2 on m1.t_name = m2.t_name
order by
  diff desc,
  tag_name asc
limit 100;
