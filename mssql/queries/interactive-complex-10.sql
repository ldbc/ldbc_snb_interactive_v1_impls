/* Q10. Friend recommendation
      "LdbcQuery10" : {
        "personIdQ10" : :personId,
        "month" : 2,
        "limit" : 10
      }
 */
SELECT TOP(10)
    Person.personId,
    firstName,
    lastName,
    (
        SELECT count(DISTINCT Message.MessageId)
        FROM Message, Message_hasTag_Tag pt1
        WHERE CreatorPersonId = Person.personId
          AND ParentMessageId IS NULL -- post, not comment
          AND Message.MessageId = pt1.MessageId
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
        WHERE CreatorPersonId = Person.personId
          AND ParentMessageId IS NULL -- post, not comment
          AND NOT EXISTS (
              SELECT *
              FROM Person_hasInterest_Tag, Message_hasTag_Tag
              WHERE Person_hasInterest_Tag.PersonId = :personId
                AND Person_hasInterest_Tag.TagId = Message_hasTag_Tag.TagId
                AND Message_hasTag_Tag.MessageId = Message.MessageId
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
WHERE 
  LocationCityId = City.id
  AND Person.personId = f.Person2Id
  AND ( 
    (MONTH(birthday) = :month AND DAY(birthday) >= 21)
    OR 
    (MONTH(birthday) = (:month % 12 + 1) AND DAY(birthday) <  22)
  )
ORDER BY commonInterestScore DESC, Person.personId;
