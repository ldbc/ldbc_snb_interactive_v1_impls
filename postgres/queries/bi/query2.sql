-- Q2. Top tags for country, age, gender, time
-- date1: '2009-12-31T23:00:00.000+00:00'::timestamp
-- date2: '2010-11-07T23:00:00.000+00:00'::timestamp
-- country1: Ethiopia
-- country2: Belarus
select
  ctry_name,
  mm,
  p_gender,
  age,
  t_name, count(*) as cnt
from
  (
    -- according to the specs, the end of simulation is hard-coded as 2013-01-01
    select p_gender, floor(2013) - extract(year from (p_birthday))/5 as age, ps_postid, extract(month from ps_creationdate) as mm, ctry_name
    from person, post, country
    where ps_creatorid = p_personid
      and p_placeid = ctry_city
      and ps_creationdate >= $date1
      and ps_creationdate <= $date2
      and ctry_name in ('$country1', '$country2')
  ) person_posts,
  post_tag, tag
where ps_postid = pst_postid
  and t_tagid = pst_tagid
group by ctry_name, mm, p_gender, age, t_name
having count(*) > 100
order by
  cnt desc,
  t_name asc,
  age asc,
  p_gender asc,
  mm asc,
  ctry_name asc
limit 100;
