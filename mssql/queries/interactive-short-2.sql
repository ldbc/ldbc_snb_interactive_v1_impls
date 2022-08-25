/* IS2. Recent messages of a person
\set personId 10995116277795
 */
;WITH recentMessages AS (
    SELECT TOP(10) messageId, messageContent, messageCreationDate, originalParentMessageId, originalPostId, creatorPersonId
    FROM (
        SELECT 
            m1.MessageId as messageId,
            COALESCE(m1.imageFile, m1.content, '') as messageContent,
            m1.creationDate as messageCreationDate,
            LAST_VALUE(m2.ParentMessageId)  WITHIN GROUP (GRAPH PATH) AS originalParentMessageId,
            LAST_VALUE(m2.MessageId)        WITHIN GROUP (GRAPH PATH) AS originalPostId,
            LAST_VALUE(m2.CreatorPersonId)  WITHIN GROUP (GRAPH PATH) AS creatorPersonId
        FROM
            Message m1,
            Message_replyOf_Message FOR PATH,
            Message FOR PATH AS m2
        WHERE MATCH(SHORTEST_PATH(m1(-(Message_replyOf_Message)->m2)+))
        AND m1.CreatorPersonId = :personId
    ) AS Q
    WHERE Q.originalParentMessageId IS NULL
    ORDER BY messageCreationDate DESC
)
SELECT recentMessages.MessageId, recentMessages.messageContent, recentMessages.messageCreationDate,
       recentMessages.originalPostId, p2.PersonId, p2.firstName, p2.lastName
FROM recentMessages
LEFT JOIN Person p2 ON p2.personId = recentMessages.creatorPersonId
ORDER BY recentMessages.messageCreationDate DESC, recentMessages.originalPostId DESC;
