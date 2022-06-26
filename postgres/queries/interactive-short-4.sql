/* IS4. Content of a message
\set messageId 824633720985
 */
select COALESCE(imagefile, content, ''), creationdate
from message
where MessageId = :messageId;
;
