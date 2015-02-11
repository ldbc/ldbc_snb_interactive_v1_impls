select top 100 p_personid, n_posts, n_replies, n_likes, n_posts + 2 * n_replies + 10 * n_likes as sc
from
  (select p_personid, count (*) as n_posts, sum (n_lks) as n_likes, sum (n_reps) as n_replies
  from
    (select p_personid, ps_postid, 
      (select count (*) from likes where l_postid = ps_postid) as n_lks,
      (select count (*) from post p2 where p2.ps_replyof = p1.ps_postid) as n_reps 
     from person, post p1, post_tag, tag
     where ps_creatorid = p_personid 
     and pst_postid = ps_postid and t_tagid = pst_tagid and t_name = 'Winston_Churchill'
     ) psts
  group by p_personid) sums
order by sc desc, p_personid asc;

