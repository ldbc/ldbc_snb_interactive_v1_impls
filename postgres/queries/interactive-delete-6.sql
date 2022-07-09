/* DEL 6: Remove post thread
\set postId 549755813906
 */
DELETE FROM Message
WHERE id = :postId
;
