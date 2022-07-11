/* IS2. Recent messages of a Person
\set personId 17592186044461
 */
WITH RECURSIVE cposts(id, content, imageFile, creationDate, ParentMessageId, CreatorPersonId) AS (
    SELECT id, content, imageFile, creationDate, ParentMessageId, CreatorPersonId
    FROM Message
    WHERE CreatorPersonId = :personId
    ORDER BY creationDate DESC
    LIMIT 10
), parent(postid, ParentMessageId, orig_postid, CreatorPersonId) AS (
    SELECT id, ParentMessageId, id, CreatorPersonId
    FROM cposts
    UNION ALL
    SELECT Message.id, Message.ParentMessageId, parent.orig_postid, Message.CreatorPersonId
    FROM Message, parent
    WHERE Message.id = parent.ParentMessageId
)
SELECT
    m1.id,
    coalesce(imageFile, content, ''),
    m1.creationDate,
    m2.id,
    m2.PersonId,
    m2.firstName,
    m2.lastName
FROM 
    (SELECT id, content, imageFile, creationDate, ParentMessageId FROM cposts) m1
LEFT JOIN
    (
        SELECT orig_postid, postid AS id, Person.id AS PersonId, firstName, lastName
        FROM parent, Person
        WHERE ParentMessageId IS NULL AND parent.CreatorPersonId = Person.id
    ) m2
    ON m2.orig_postid = m1.id
ORDER BY creationDate DESC, m2.id DESC
;
