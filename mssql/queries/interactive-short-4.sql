/* IS4. Content of a message
\set messageId 206158431836
 */
SELECT COALESCE(m_ps_imagefile, m_content, ''), m_creationdate
  FROM message
 WHERE m_messageid = :messageId;
;
