/* IS6. Forum of a message
\set messageId 206158431836
 */
WITH RECURSIVE chain(parent, child) as(
    SELECT m_c_replyof, m_messageid FROM message where m_messageid = :messageId
    UNION ALL
    SELECT p.m_c_replyof, p.m_messageid FROM message p, chain c where p.m_messageid = c.parent 
)
select f_forumid, f_title, p_personid, p_firstname, p_lastname
from message, person, forum
where m_messageid = (select coalesce(min(parent), :messageId) from chain)
  and m_ps_forumid = f_forumid and f_moderatorid = p_personid;
;
