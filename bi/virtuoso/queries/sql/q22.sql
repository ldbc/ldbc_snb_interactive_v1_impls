-- Maybe we have to limit the number of returned persons
select contact.p_personid, contact.p_firstname, contact.p_lastname, sum (score) 
from person contact, related, person contacted, country target, country source  
where contact.p_personid = p1 and contacted.p_personid = p2
      and contacted.p_placeid = target.ctry_city and target.ctry_name = 'Yemen'
      and contact.p_placeid = source.ctry_city and source.ctry_name = 'United_States'
group by contact.p_personid, contact.p_firstname, contact.p_lastname 
order by score desc;
