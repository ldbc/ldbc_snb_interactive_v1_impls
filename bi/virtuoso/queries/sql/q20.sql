-- tagclass frequency 
select tc_name, count (*) as cnt
from tagclass, tag_tagclass, tag, post_tag
where tc_tagclassid = ttc_tagclassid
      and ttc_tagid = t_tagid
      and pst_tagid = ttc_tagid
group by tc_name
order by cnt desc;
