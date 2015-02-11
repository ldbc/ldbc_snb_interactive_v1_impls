select top 200  f_forumid, sum (competing) as comp, sum (ours) as ours2
from
  (select f_forumid, (select count (*) from post
         where ps_forumid = f_forumid
         and exists (select 1 from post_tag where pst_postid = ps_postid 
                    and pst_tagid in
                        (select ttc_tagid from tagclass, tag_tagclass
                where ttc_tagclassid = tc_tagclassid and tc_name = 'MusicalArtist')
          )
        ) as competing,
    (select count (*) from post
    where ps_forumid = f_forumid
    and exists (select 1 from post_tag where pst_postid = ps_postid 
                   and pst_tagid in
               (select ttc_tagid from tagclass, tag_tagclass where ttc_tagclassid = tc_tagclassid
         and tc_name = 'Writer')
      )
    ) as ours 
  from forum
  where (select count (*) from forum_person where fp_forumid = f_forumid) > 200
  )   mindshare 
where competing > 0 and ours > 0
group by f_forumid
order by comp - ours2 desc, f_forumid;

