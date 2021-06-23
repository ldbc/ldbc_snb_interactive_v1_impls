/* IS4. Content of a message
\set messageId 206158431836
 */
select COALESCE(m_ps_imagefile, m_content, ''), m_creationdate
from message
where m_messageid = :messageId;
;
