/* DEL 3: Remove comment like
\set personId 8796093023099
\set commentId 1099511632040
 */
DELETE FROM Person_likes_Message
WHERE PersonId = :personId
  AND id = :commentId
;
