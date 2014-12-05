select top 100 p.p_personid, count (p1.p_personid) as cnt 
from person p, knows, country c, person p1, country c1
where c.ctry_name = 'Israel' and p.p_placeid = c.ctry_city
      and k_person1id = p.p_personid 
      and k_person2id = p1.p_personid
      and p1.p_placeid = c1.ctry_city
      and c1.ctry_name = c.ctry_name
group by p.p_personid 
having count(p1.p_personid) = floor ((select avg (fcnt)
                   from (select p2.p_personid, count (p3.p_personid) as fcnt
         from person p2, knows, country c2, person p3, country c3
         where p2.p_placeid = c3.ctry_city and c3.ctry_name = 'Israel'
           and k_person1id = p2.p_personid
           and k_person2id = p3.p_personid
           and p3.p_placeid = c3.ctry_city
           and c3.ctry_name = c2.ctry_name
         group by p2.p_personid) ctavg)
       )
order by p.p_personid;

