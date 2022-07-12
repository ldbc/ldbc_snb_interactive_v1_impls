/* Q10. Friend recommendation
\set personId 17592186044461
\set month 5
 */
SELECT
    Person.id,
    firstName,
    lastName,
    (
        SELECT count(DISTINCT Message.id)
        FROM Message, Message_hasTag_Tag pt1
        WHERE CreatorPersonId = Person.id
          AND ParentMessageId IS NULL -- post, not comment
          AND Message.id = pt1.id
          AND EXISTS (
              SELECT *
              FROM Person_hasInterest_Tag
              WHERE Person_hasInterest_Tag.PersonId = :personId
                AND Person_hasInterest_Tag.TagId = pt1.TagId
          )
    ) -
    (
        SELECT count(*)
        FROM Message
        WHERE CreatorPersonId = Person.id
          AND ParentMessageId IS NULL -- post, not comment
          AND NOT EXISTS (
              SELECT *
              FROM Person_hasInterest_Tag, Message_hasTag_Tag
              WHERE Person_hasInterest_Tag.PersonId = :personId
                AND Person_hasInterest_Tag.TagId = Message_hasTag_Tag.TagId
                AND Message_hasTag_Tag.id = Message.id
          )
    ) AS commonInterestScore,
    gender,
    City.name
FROM
    Person,
    City,
    (
        SELECT DISTINCT k2.Person2Id
        FROM Person_knows_Person k1, Person_knows_Person k2
        WHERE k1.Person1Id = :personId
          AND k1.Person2Id = k2.Person1Id
          AND k2.Person2Id <> :personId
          AND NOT EXISTS (
              SELECT *
              FROM Person_knows_Person
              WHERE Person1Id = :personId
                AND Person2Id = k2.Person2Id
          )
    ) f
WHERE LocationCityId = City.id
  AND Person.id = f.Person2Id
  AND (
      (extract(month FROM birthday) = :month          AND (CASE WHEN extract(day FROM birthday) >= 21 THEN true ELSE false END))
   OR (extract(month FROM birthday) = :month % 12 + 1 AND (CASE WHEN extract(day FROM birthday) <  22 THEN true ELSE false END))
  )
ORDER BY commonInterestScore DESC, Person.id
LIMIT 10
;
