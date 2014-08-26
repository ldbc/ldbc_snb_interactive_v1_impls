select top 100 p_personid, count (*) as cnt 
from person, knows, country 
where ctry_name = 'China' and p_placeid = ctry_city
      and k_person1id = p_personid 
group by p_personid 
having cnt = floor ((select avg (fcnt)
       	     	     from (select p_personid, count (*) as fcnt
		     	   from person, knows, country 
			   where p_placeid = ctry_city and ctry_name = 'China'
			   	 and k_person1id = p_personid
			   group by p_personid) ctavg)
		   )
order by p_personid;
