/* IS2. Recent messages of a person
\set personId 10995116277795
 */
WITH cposts(MessageId
          , content
          , imageFile
          , creationDate
          , ParentMessageId
          , CreatorPersonId) AS (
            SELECT TOP(10) MessageId
                         , content
                         , imageFile
                         , creationDate
                         , ParentMessageId
                         , CreatorPersonId
              FROM message
             WHERE CreatorPersonId = :personId
          ORDER BY creationDate DESC
            ), parent(postid, ParentMessageId, orig_postid, CreatorPersonId) AS (
            SELECT MessageId
                 , ParentMessageId
                 , MessageId
                 , CreatorPersonId 
              FROM cposts
         UNION ALL
            SELECT MessageId
                 , ParentMessageId
                 , orig_postid
                 , CreatorPersonId
            FROM Message, parent
            WHERE Message.MessageId = parent.ParentMessageId
)
SELECT p1.MessageId, COALESCE(imageFile, content, ''), p1.creationDate,
       p2.MessageId, p2.PersonId, p2.firstName, p2.lastName
FROM 
     (SELECT MessageId, content, imageFile, creationDate, ParentMessageId FROM cposts
     ) p1
LEFT JOIN
     (
        SELECT orig_postid, postid AS MessageId, Person.id AS PersonId, firstName, lastName
        FROM parent, Person
        WHERE ParentMessageId IS NULL AND parent.CreatorPersonId = Person.id
     )p2  
ON p2.orig_postid = p1.MessageId
ORDER BY creationDate DESC, p2.MessageId DESC;
;
