with roots(r_tagclassid) as 
( select distinct s_supertagclassid from subclass s1
  where not exists(select * from subclass s2
    where s2.s_subtagclassid = s1.s_supertagclassid)
), subroots(s_tagclassid) as
( select distinct s_subtagclassid from subclass s 
where s.s_supertagclassid in (select r_tagclassid from roots)
), 
recursive hierarchy(h_subtagclassid, h_supertagclassid) as (
select s_subtagclassid, s_supertagclassid from subclass s 
where s.s_supertagclassid in (select s_tagclassid from subroots)
union 
select s.s_subtagclassid, h.h_supertagclassid from subclass s, hierarchy h 
where s.s_supertagclassid = h.h_subtagclassid
)
select tc_name, count (*) as cnt
from tagclass, tag_tagclass, tag, post_tag, hierarchy
where t_tagid =  ttc_tagid
     and ttc_tagclassid = h_subtagclassid
     and h_supertagclassid = tc_tagclassid
     and pst_tagid=ttc_tagclassid
group by tc_name
order by cnt desc, tc_name;

