-- Most liked poster on tag, likes from much liked posters count for more
--  - Tag (Linear to number of posts)

with
  popularity as (
    select ps_creatorid as pop_personid, count(*) as pop_auth
    from post, likes where ps_postid = l_postid
    group by ps_creatorid
  ),
  post_tag as (
    select * from post join post_tag on pst_postid = ps_postid
    join tag on pst_tagid = t_tagid where t_name = '--1--'
  ),
  post_tag_likes as (
    select * from post_tag left join likes on ps_postid = l_postid
  )
select ps_creatorid, sum(coalesce(popularity.pop_auth,0)) as sc
from post_tag_likes left join popularity on pop_personid = l_personid
group by ps_creatorid
order by
  sc desc,
  ps_creatorid asc
limit 100;
