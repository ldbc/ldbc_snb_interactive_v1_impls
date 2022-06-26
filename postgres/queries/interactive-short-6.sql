/* IS6. Forum of a message
\set messageId 824633720985
 */
WITH RECURSIVE chain(parent, child) as(
    SELECT ParentMessageId, MessageId
    FROM Message
    WHERE MessageId = :messageId
    UNION ALL
    SELECT p.ParentMessageId, p.MessageId
    FROM Message p, chain c
    WHERE p.MessageId = c.parent
)
SELECT
  Forum.id,
  Forum.title,
  Person.id,
  Person.firstName,
  Person.lastName
FROM Message, Person, Forum
WHERE MessageId = (SELECT coalesce(min(parent), :messageId) FROM chain)
  AND Message.ContainerForumId = Forum.id
  AND ModeratorPersonId = Person.id
;
