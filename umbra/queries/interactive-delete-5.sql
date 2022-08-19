/* DEL 5: Remove forum membership
\set forumId 1030792151060
\set personId 8796093023099
 */
DELETE FROM Forum_hasMember_Person
WHERE ForumId = :forumId
  AND PersonId = :personId
;
