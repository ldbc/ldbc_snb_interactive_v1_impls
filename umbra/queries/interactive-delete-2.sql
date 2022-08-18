/* DEL 2: Remove post like
\set personId 8796093023099
\set postId 549755813906
 */
DELETE FROM Person_likes_Message
WHERE PersonId = :personId
  AND id = :postId
;
