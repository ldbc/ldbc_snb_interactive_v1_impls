with related(p1, p2, score) as 
(select  rep.ps_creatorid as p1, org.ps_creatorid as p2, 4 as score
  from post org, post rep where rep.ps_replyof = org.ps_postid
union  all
select org.ps_creatorid as p1, rep.ps_creatorid as p2, 1 as score
  from post org, post rep where rep.ps_replyof = org.ps_postid
union  all
select  k_person1id, k_person2id, 15 as score from knows
union  all
  select l_personid, ps_creatorid, 10 from likes, post where l_postid = ps_postid
union all 
  select  ps_creatorid, l_personid, 1 from likes, post where l_postid = ps_postid),
 allscores(p_personid, p_firstname, p_lastname, p_city, score) as
( select contact.p_personid, contact.p_firstname, contact.p_lastname,  contact.p_placeid, sum (score) as s
  from person contact, related, person contacted, country target, country source  
where contact.p_personid = p1 and contacted.p_personid = p2
and contacted.p_placeid = target.ctry_city and target.ctry_name = 'Yemen'
and contact.p_placeid = source.ctry_city and source.ctry_name = 'United_States'
group by contact.p_personid, contact.p_firstname, contact.p_lastname,contact.p_placeid
order by s desc
)
select * 
from  allscores total, (select p_city, max(score) as score from allscores group by p_city) percity
where total.p_city=percity.p_city and total.score =percity.score
;

