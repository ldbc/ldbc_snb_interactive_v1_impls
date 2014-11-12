-- Top tags for country, age, gender, time

create procedure p_age_group (in bday date) returns int
{
  return floor (datediff ('year', bday, cast ('2014-1-1' as date)) / 5);
}

select top 100 ctry_name, month (ps_creationdate) as mm, p_gender,
       p_age_group (p_birthday) as age, t_name, count (*) as cnt
from person, post, post_tag, tag, country 
where ps_creatorid = p_personid and p_placeid = ctry_city
      and pst_postid = ps_postid and t_tagid = pst_tagid 
      and ps_creationdate between cast ('2012-1-1' as date)
      and dateadd ('year', 1, cast ('2012-1-1'as date))
      and (ctry_name = 'United_States'or ctry_name = 'Canada')
group by ctry_name, mm, p_gender, age, t_name
having cnt > 1000 
order by cnt desc
;
