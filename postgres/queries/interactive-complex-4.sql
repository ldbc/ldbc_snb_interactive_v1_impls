/* Q4. New topics
\set personId 17592186044461
\set startDate '\'2010-10-01\''::date
\set durationDays 31
 */
SELECT name, count(*) AS postCount
FROM
    Tag,
    Message,
    Message_hasTag_Tag recent,
    Person_knows_Person
WHERE Message.id = recent.id
  AND recent.TagId = Tag.id
  AND Message.CreatorPersonId = Person2Id
  AND ParentMessageId IS NULL -- post, not comment
  AND Person1Id = :personId
  AND Message.creationDate >= :startDate
  AND Message.creationDate < (:startDate + INTERVAL '1 days' * :durationDays)
  AND NOT EXISTS (
        SELECT *
        FROM (
            SELECT DISTINCT TagId
            FROM Message, Message_hasTag_Tag, Person_knows_Person
            WHERE Person1Id = :personId
              AND Person2Id = CreatorPersonId
              AND ParentMessageId IS NULL -- post, not comment
              AND Message_hasTag_Tag.id = Message.id
              AND Message.creationDate < :startDate
        ) tags
        WHERE tags.TagId = recent.TagId
      )
GROUP BY name
ORDER BY postCount DESC, name ASC
LIMIT 10
;
