/* Q6. Tag co-occurrence
\set personId 17592186044461
\set tagName '\'Carl_Gustaf_Emil_Mannerheim\''
 */
SELECT name, count(*) AS postcount
FROM
    Tag,
    Message_hasTag_Tag,
    Message,
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
WHERE CreatorPersonId = friend.Person2Id
  AND ParentMessageId IS NULL -- post, not comment
  AND Message.id = Message_hasTag_Tag.id
  AND Message_hasTag_Tag.TagId = Tag.id
  AND name <> :tagName
  AND EXISTS (
          SELECT *
          FROM Tag, Message_hasTag_Tag
          WHERE Message.id = Message_hasTag_Tag.id
            AND Message_hasTag_Tag.TagId = Tag.id
            AND Tag.name = :tagName
      )
GROUP BY name
ORDER BY postCount DESC, name ASC
LIMIT 10
;
