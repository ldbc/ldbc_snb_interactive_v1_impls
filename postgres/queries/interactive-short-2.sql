/* IS2. Recent messages of a Person
\set personId 17592186044461
 */
WITH RECURSIVE cposts(MessageId, content, imageFile, creationDate, ParentMessageId, CreatorPersonId) AS (
    SELECT MessageId, content, imageFile, creationDate, ParentMessageId, CreatorPersonId
    FROM Message
    WHERE CreatorPersonId = :personId
    ORDER BY creationDate DESC
    LIMIT 10
), parent(postid, ParentMessageId, orig_postid, CreatorPersonId) AS (
    SELECT MessageId, ParentMessageId, MessageId, CreatorPersonId
    FROM cposts
    UNION ALL
    SELECT Message.MessageId, Message.ParentMessageId, parent.orig_postid, Message.CreatorPersonId
    FROM Message, parent
    WHERE Message.MessageId = parent.ParentMessageId
)
SELECT
    m1.MessageId,
    coalesce(imageFile, content, ''),
    m1.creationDate,
    m2.MessageId,
    m2.PersonId,
    m2.firstName,
    m2.lastName
FROM 
    (SELECT MessageId, content, imageFile, creationDate, ParentMessageId FROM cposts) m1
LEFT JOIN
    (
        SELECT orig_postid, postid AS MessageId, Person.id AS PersonId, firstName, lastName
        FROM parent, Person
        WHERE ParentMessageId IS NULL AND parent.CreatorPersonId = Person.id
    ) m2
    ON m2.orig_postid = m1.MessageId
ORDER BY creationDate DESC, m2.MessageId DESC
;
