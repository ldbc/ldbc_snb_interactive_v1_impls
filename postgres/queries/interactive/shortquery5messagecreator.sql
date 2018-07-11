select p_personid, p_firstname, p_lastname
from post, person
where ps_postid = :messageId and ps_creatorid = p_personid;
