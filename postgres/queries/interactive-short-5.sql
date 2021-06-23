/* IS5. Creator of a message
\set messageId 206158431836
 */
select p_personid, p_firstname, p_lastname
from message, person
where m_messageid = :messageId and m_creatorid = p_personid;
;
