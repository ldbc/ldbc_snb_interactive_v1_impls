SELECT TOP(20)
    title,
    count(MessageId) AS postCount
FROM
    (
        SELECT title, Forum.id AS forumid, friend.Person2Id
        FROM Forum, Forum_hasMember_Person,
        (
            SELECT Person2Id
            FROM Person_knows_Person
            WHERE Person1Id = :personId
            UNION
            SELECT k2.Person2Id
            FROM Person_knows_Person k1, Person_knows_Person k2
            WHERE k1.Person1Id = :personId
              AND k1.Person2Id = k2.Person1Id
              AND k2.Person2Id <> :personId
        ) friend
        WHERE Forum.id = Forum_hasMember_Person.ForumId
          AND Forum_hasMember_Person.PersonId = friend.Person2Id
          AND Forum_hasMember_Person.creationDate >= :minDate
    ) tmp
LEFT JOIN Message
  ON tmp.forumid = Message.ContainerForumId
 AND CreatorPersonId = tmp.Person2Id
GROUP BY forumid, title
ORDER BY postCount DESC, forumid ASC
;
