/* Q5 New groups
\set personId 17592186044461
\set minDate '\'2010-11-01\''::date
 */
SELECT
    title,
    count(id) AS postCount
FROM
    (
        SELECT title, Forum.id AS ForumId, friend.Person2Id
        FROM Forum, Forum_hasMember_Person,
        (
            SELECT Person2Id
            FROM Person_knows_Person
            WHERE Person1Id = :personId
            UNION
            SELECT k2.Person2Id
            FROM Person_knows_Person k1
            JOIN Person_knows_Person k2
              ON k1.Person2Id = k2.Person1Id
            WHERE k1.Person1Id =  :personId
              AND k2.Person2Id <> :personId
        ) friend
        WHERE Forum.id = Forum_hasMember_Person.ForumId
          AND Forum_hasMember_Person.PersonId = friend.Person2Id
          AND Forum_hasMember_Person.creationDate >= :minDate
    ) tmp
LEFT JOIN Message
       ON tmp.ForumId = Message.ContainerForumId
      AND CreatorPersonId = tmp.Person2Id
      AND Message.ParentMessageId IS NULL
GROUP BY ForumId, title
ORDER BY postCount DESC, ForumId ASC
LIMIT 20
;
