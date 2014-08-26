-- Triangles in India 
-- Problem: ctry.pl_type = 'country' is not neccessary
select count (*)
from knows k1, knows k2, knows k3 
where k1.k_person1id = k3.k_person2id
      and k1.k_person2id = k2.k_person1id
      and  k2.k_person2id = k3.k_person1id
      and k1.k_person1id < k1.k_person2id and k2.k_person1id < k2.k_person2id
      and k1.k_person1id in
      	  (select p_personid from person, place city, place ctry
	   where p_placeid = city.pl_placeid and city.pl_containerplaceid = ctry.pl_placeid
	   	 and ctry.pl_name = 'India' and ctry.pl_type = 'country')
      and k2.k_person1id in
      	  (select p_personid from person, place city, place ctry
	   where p_placeid = city.pl_placeid and city.pl_containerplaceid = ctry.pl_placeid
	   	 and ctry.pl_name = 'India'  and ctry.pl_type = 'country')
      and k3.k_person1id in
      	  (select p_personid from person, place city, place ctry
	   where p_placeid = city.pl_placeid and city.pl_containerplaceid = ctry.pl_placeid
	   	 and ctry.pl_name = 'India'  and ctry.pl_type = 'country')
;
