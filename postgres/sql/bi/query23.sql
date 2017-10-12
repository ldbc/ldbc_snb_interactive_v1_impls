select
  dest.pl_name,
  extract(month from ps_creationdate) as mm,
  count(*) as cnt
from person, post, place dest, country resid
where resid.ctry_name = '--1--'
  and dest.pl_name <> '--1--'
  and p_placeid = resid.ctry_city
  and ps_locationid = dest.pl_placeid
  and p_personid = ps_creatorid
group by dest.pl_name, extract(month from ps_creationdate)
order by
  cnt desc,
  dest.pl_name asc
limit 100;
