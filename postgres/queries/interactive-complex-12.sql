/* Q12. Expert search
\set personId 17592186044461
\set tagClassName '\'Monarch\''
 */
WITH RECURSIVE extended_tags(s_subtagclassid, s_supertagclassid) AS (
    SELECT id, id
    FROM tagclass
    UNION
    SELECT tc.id, t.s_supertagclassid
      FROM tagclass tc, extended_tags t
     WHERE tc.SubclassOfTagClassId = t.s_subtagclassid
)
SELECT
    Person.id,
    firstName,
    lastName,
    array_agg(DISTINCT name),
    count(DISTINCT m1.id) AS replyCount
FROM
    Person,
    Message m1,
    Person_knows_Person,
    Message m2,
    Message_hasTag_Tag, 
    (
        SELECT DISTINCT id, name
        FROM tag
        WHERE TypeTagClassId IN (
            SELECT DISTINCT s_subtagclassid
            FROM extended_tags k, tagclass
            WHERE id = k.s_supertagclassid
              AND name = :tagClassName
            )
    ) selected_tags
WHERE Person1Id = :personId
  AND Person2Id = Person.id
  AND Person.id = m1.CreatorPersonId
  AND m1.ParentMessageId = m2.id
  AND m2.ParentMessageId IS NULL
  AND m2.id = Message_hasTag_Tag.id
  AND Message_hasTag_Tag.TagId = selected_tags.id
GROUP BY Person.id, Person.firstName, person.lastName
ORDER BY replyCount DESC, Person.id ASC
LIMIT 20
;
