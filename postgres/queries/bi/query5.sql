-- top posters in top 100 forums
--  - Country (linear to number of posts in country)
select
  p_personid,
  p_firstname,
  p_lastname,
  p_creationdate,
  count(ps_postid)
from
  (
    select f_forumid, count(*) as cnt
    from forum, forum_person, person, country
    where f_forumid = fp_forumid
     and p_personid = fp_personid
     and p_placeid = ctry_city
     and ctry_name = '$country'
    group by f_forumid order by cnt desc, f_forumid asc limit 100
  ) tf
  inner join post on ps_forumid = f_forumid
  right outer join (
    select distinct person.*
    from
    (
       select f_forumid, f_moderatorid, count(*) as cnt
       from forum, forum_person, person, country
       where f_forumid = fp_forumid
         and p_personid = fp_personid
         and p_placeid = ctry_city
         and ctry_name = '$country'
       group by f_forumid, f_moderatorid
       order by cnt desc, f_forumid asc limit 100
    ) tf
    inner join forum_person on fp_forumid = f_forumid
    inner join person on fp_personid = p_personid
                      or p_personid = f_moderatorid
  ) person on ps_creatorid = p_personid
group by p_personid, p_firstname, p_lastname, p_creationdate
order by
  count(ps_creatorid) desc,
  p_personid asc
limit 100;
