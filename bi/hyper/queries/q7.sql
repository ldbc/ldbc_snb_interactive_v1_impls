select top 100 ps_creatorid, sum (likeauth.auth) as sc
from post, tag, post_tag, likes l,
     (select ps_creatorid as liker, count (*) as auth
     from post, likes where ps_postid = l_postid
     group by ps_creatorid) likeauth
where liker = l_personid and l_postid = ps_postid 
      and pst_postid = ps_postid 
      and   pst_tagid = t_tagid and t_name = 'Augustine_of_Hippo'
group by ps_creatorid
order by sc desc, ps_creatorid asc;

