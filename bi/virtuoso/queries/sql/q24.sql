-- tag timeline 
select year (ps_creationdate) as yy, month (ps_creationdate) as mm, continent, count (*) as n_posts, 
  sum ((select count (*) from likes where l_postid = ps_postid)) as n_likes
from post join post_tag on pst_postid = ps_postid 
     left join
     (select cont.pl_name as continent, ctry.pl_placeid as pl
      from place cont, place ctry
      where cont.pl_placeid = ctry.pl_containerplaceid
     ) ppl on pl = ps_locationid
where pst_tagid in
      		(select ttc_tagid from tag_tagclass, tagclass
		 where tc_name = 'Politician' and  ttc_tagclassid = tc_tagclassid)
group by yy, mm, continent 
order by yy, mm, continent;
