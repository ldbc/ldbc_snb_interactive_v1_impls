/* IS5. Creator of a message
\set messageId 206158431836
 */
SELECT p_personid, p_firstname, p_lastname
  FROM message, person
 WHERE m_messageid = :messageId AND m_creatorid = p_personid;
;
