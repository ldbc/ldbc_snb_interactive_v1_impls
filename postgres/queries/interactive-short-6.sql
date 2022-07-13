/* IS6. Forum of a message
\set messageId 824633720985
 */
WITH RECURSIVE chain(parent, child) as(
    SELECT ParentMessageId, id
    FROM Message
    WHERE id = :messageId
    UNION ALL
    SELECT ParentMessageId, id
    FROM Message, chain c
    WHERE id = parent
)
SELECT
  Forum.id,
  Forum.title,
  Person.id,
  Person.firstName,
  Person.lastName
FROM Message, Person, Forum
WHERE Message.id = (SELECT coalesce(min(parent), :messageId) FROM chain)
  AND Message.ContainerForumId = Forum.id
  AND ModeratorPersonId = Person.id
;
