/* IS4. Content of a message
\set messageId 824633720985
 */
SELECT coalesce(imageFile, content, ''), creationDate
FROM Message
WHERE id = :messageId
;
