-- Top vacation destination for country per month
select top 100 dest.pl_name, month (ps_creationdate) as mm, count (*) as cnt
from person, post, place dest, country resid 
where resid.ctry_name = 'United_States' and dest.pl_name <> 'United_States'
      and p_placeid = resid.ctry_city and ps_locationid = dest.pl_placeid 
      and p_personid = ps_creatorid 
group by dest.pl_name, mm 
order by cnt desc;
