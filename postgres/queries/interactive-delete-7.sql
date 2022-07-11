/* DEL 7: Remove comment subthread
\set commentId 1099511632040
 */
DELETE FROM Message
WHERE id = :commentId
;
