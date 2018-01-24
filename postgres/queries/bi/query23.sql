/* Q23. Holiday destinations
\set country  '\'Belarus\''
 */
select
  count(*) as cnt,
  dest.pl_name,
  extract(month from ps_creationdate) as mm
from person, post, place dest, country resid
where resid.ctry_name = :country
  and dest.pl_name <> :country
  and p_placeid = resid.ctry_city
  and ps_locationid = dest.pl_placeid
  and p_personid = ps_creatorid
group by dest.pl_name, extract(month from ps_creationdate)
order by
  cnt desc,
  dest.pl_name asc,
  mm
limit 100;
