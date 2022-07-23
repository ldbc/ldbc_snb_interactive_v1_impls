/* DEL 4: Remove forum and its content
\set forumId 1030792151060
 */
DELETE FROM Forum
WHERE id = :forumId
;
