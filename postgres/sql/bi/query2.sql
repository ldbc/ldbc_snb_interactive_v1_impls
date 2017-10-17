-- Top tags for country, age, gender, time
--  - Country names (Needs number of posts per country)(Complexity linear to total number of posts written by persons in all countries)
--  - Current date (does not effect complexity)
--  - Time range (Needs number of posts per month)

select
  ctry_name,
  mm,
  p_gender,
  age,
  t_name, count(*) as cnt
from
  (
    select p_gender, floor((extract(year from (--4--)) - extract(year from (p_birthday)))/5) as age, ps_postid, extract(month from ps_creationdate) as mm, ctry_name
    from person, post, country
    where ps_creatorid = p_personid
      and p_placeid = ctry_city
      and ps_creationdate >= --1--
      and ps_creationdate <= --2--
      and ctry_name in (--3--)
  ) person_posts,
  post_tag, tag
where ps_postid = pst_postid
  and t_tagid = pst_tagid
group by ctry_name, mm, p_gender, age, t_name
having count(*) > --5--
order by
  cnt desc,
  t_name asc,
  age asc,
  p_gender asc,
  mm asc,
  ctry_name asc
limit 100;
