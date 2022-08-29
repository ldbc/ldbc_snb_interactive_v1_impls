/* IS6. Forum of a message
\set messageId 824633720985
 */
;WITH chain AS (
    SELECT forumId
    FROM (
        SELECT 
            LAST_VALUE(m2.ContainerForumId) WITHIN GROUP (GRAPH PATH) AS forumId
        FROM
            Message m1,
            Message_replyOf_Message FOR PATH,
            Message FOR PATH AS m2
        WHERE MATCH(SHORTEST_PATH(m1(-(Message_replyOf_Message)->m2)+))
        AND m1.MessageId = :messageId
    ) AS Q
    WHERE Q.forumId IS NOT NULL
)
SELECT
  Forum.id,
  Forum.title,
  Person.personId,
  Person.firstName,
  Person.lastName
FROM Message, Person, Forum
WHERE MessageId = :messageId
AND Forum.id = (SELECT coalesce(min(forumId), Message.ContainerForumId) FROM chain)
  AND ModeratorPersonId = Person.personId
;