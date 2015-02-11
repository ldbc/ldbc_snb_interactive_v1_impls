select top 100 zombielikes.ps_creatorid, zombielikes.cnt as zmb, reallikes.cnt as real, 
  zombielikes.cnt::float/reallikes.cnt as score from 
(select  post.ps_creatorid as ps_creatorid, count(likes.l_creationdate) as cnt
from 
(select p_personid
from person, post
where ps_creatorid = p_personid 
group by p_personid, p_creationdate
having count(*) <  extract(day from ('2013-02-01'::date-p_creationdate))/30) noproducer, post, likes
where likes.l_personid = noproducer.p_personid and likes.l_postid=post.ps_postid
group by post.ps_creatorid
) zombielikes,
(select  post.ps_creatorid as ps_creatorid, count(likes.l_creationdate) as cnt
from post, likes 
where likes.l_postid=post.ps_postid
group by post.ps_creatorid
) reallikes,country, person p
where zombielikes.ps_creatorid = reallikes.ps_creatorid
  and p.p_placeid = ctry_city and ctry_name = 'Germany'
  and zombielikes.ps_creatorid = p.p_personid
order by zombielikes.cnt::float/reallikes.cnt desc, zombielikes.ps_creatorid;

