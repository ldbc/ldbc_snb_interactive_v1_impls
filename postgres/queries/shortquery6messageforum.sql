WITH RECURSIVE chain(parent, child) as(
	SELECT m_c_replyof, ps_postid FROM post where ps_postid = :messageId
	UNION ALL
	SELECT p.m_c_replyof, p.ps_postid FROM post p, chain c where p.ps_postid = c.parent 
)
select f_forumid, f_title, p_personid, p_firstname, p_lastname
from post, person, forum
where ps_postid = (select coalesce(min(parent), :messageId) from chain)
  and m_ps_forumid = f_forumid and f_moderatorid = p_personid;
