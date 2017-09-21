select p_personid, p_firstname, p_lastname
from post, person
where ps_postid = --1-- and ps_creatorid = p_personid;