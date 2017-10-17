WITH RECURSIVE chain(parent, child) as(
	SELECT ps_replyof, ps_postid FROM post where ps_postid = --1--
	UNION ALL
	SELECT p.ps_replyof, p.ps_postid FROM post p, chain c where p.ps_postid = c.parent 
)
select f_forumid, f_title, p_personid, p_firstname, p_lastname
from post, person, forum
where ps_postid = (select coalesce(min(parent), --1--) from chain)
  and ps_forumid = f_forumid and f_moderatorid = p_personid;