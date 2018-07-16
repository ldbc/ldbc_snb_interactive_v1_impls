select p2.ps_postid, p2.ps_content, p2.ps_creationdate, p_personid, p_firstname, p_lastname,
    (case when exists (
     	   	       select 1 from knows
		       where p1.ps_creatorid = k_person1id and p2.ps_creatorid = k_person2id)
      then TRUE
      else FALSE
      end)
from post p1, post p2, person
where
  p1.ps_postid = :messageId and p2.m_c_replyof = p1.ps_postid and p2.ps_creatorid = p_personid
order by p2.ps_creationdate desc, p2.ps_creatorid asc;
