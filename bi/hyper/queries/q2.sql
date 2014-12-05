select top 100 ctry_name, mm, p_gender, age, t_name, count(*) as cnt 
from (
select ctry_name, extract (month from ps_creationdate) as mm, p_gender,
       floor((extract(year from '2014-01-01'::date) - extract(year from p_birthday))/5) as age, 
       t_name
from person, post, post_tag, tag, country 
where ps_creatorid = p_personid and p_placeid = ctry_city
      and pst_postid = ps_postid and t_tagid = pst_tagid 
      and ps_creationdate >= '2012-01-01'::date 
      and ps_creationdate <= '2012-01-01'::date + interval '1' year      
      and (ctry_name = 'United_States'or ctry_name = 'Canada')
      ) data
group by ctry_name, mm, p_gender, age, t_name
having count(*) > 100
order by cnt desc, ctry_name
;

