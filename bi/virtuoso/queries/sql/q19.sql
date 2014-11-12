-- Teenager talks to strangers 
-- Problem: There is no person born after 1990.1.31
-- Also, there is no person born before 1980.2.1, but in table person_company there are a lot of persons worked from 1970.
select top 10 p_personid, count (*) as cnt,
       sum (case when exists (select 1 from knows
		       	      where k_person1id = teen.p_personid and k_person2id = org.ps_creatorid)
	    then 0 else 1 end) as strangercnt
from person teen, post org, post rep
where p_birthday > cast ('2000-1-1' as date)
      and rep.ps_creatorid = teen.p_personid 
      and org.ps_postid = rep.ps_replyof 
group by teen.p_personid
order by cnt desc;
