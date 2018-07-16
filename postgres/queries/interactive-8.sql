select p1.ps_creatorid, p_firstname, p_lastname, p1.ps_creationdate, p1.ps_postid, p1.ps_content
  from post p1, post p2, person
  where
      p1.m_c_replyof = p2.ps_postid and
      p2.ps_creatorid = :Person and
      p_personid = p1.ps_creatorid
order by p1.ps_creationdate desc, 5
limit 20
