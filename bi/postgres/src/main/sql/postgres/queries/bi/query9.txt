-- Where are competitor brands discussed but not this one?
--	- Tagclass A (linear to number of posts per tagclass)
--	- Tagclass B ( linear to number of posts per tagclass)

select f_forumid, sum (competing) as comp, sum (ours) as ours2
from
  (select f_forumid, (select count (*) from post
         where ps_forumid = f_forumid
         and exists (select 1 from post_tag where pst_postid = ps_postid 
                    and pst_tagid in
                        (select ttc_tagid from tagclass, tag_tagclass
                where ttc_tagclassid = tc_tagclassid and tc_name = '--1--')
          )
        ) as competing,
    (select count (*) from post
    where ps_forumid = f_forumid
    and exists (select 1 from post_tag where pst_postid = ps_postid 
                   and pst_tagid in
               (select ttc_tagid from tagclass, tag_tagclass where ttc_tagclassid = tc_tagclassid
         and tc_name = '--2--')
      )
    ) as ours 
  from forum
  where (select count (*) from forum_person where fp_forumid = f_forumid) > --3--
  )   mindshare 
where competing > 0 and ours > 0
group by f_forumid
order by abs(sum (ours)-sum (competing)) desc, f_forumid
limit 100;

