select p_personid, p_firstname, p_lastname, k_creationdate
from knows, person
where k_person1id = :personId and k_person2id = p_personid
order by k_creationdate desc, p_personid asc;
;
