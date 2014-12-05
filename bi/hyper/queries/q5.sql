select top 100 p_personid, p_firstname, p_lastname, p_creationdate, count (*) 
from person, post, forum f,
     (select top 100 f_forumid, count (*) as cnt
     from forum, forum_person, person, country 
     where f_forumid = fp_forumid and p_personid = fp_personid 
     and p_placeid = ctry_city and ctry_name = 'China'
     group by f_forumid order by cnt desc) tf
where ps_creatorid = p_personid
      and ps_forumid = f.f_forumid 
      and f.f_forumid = tf.f_forumid
      and p_personid = ps_creatorid
group by p_personid,p_firstname, p_lastname, p_creationdate
order by count(*) desc, p_personid;

