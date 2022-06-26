/* Q3. Friends and friends of friends that have been to given countries
\set personId 17592186044461
\set countryXName '\'Angola\''
\set countryYName '\'Colombia\''
\set startDate '\'2010-06-01\''::date
\set durationDays 28
 */
SELECT
    Person.id AS otherPersonId,
    Person.firstName AS otherPersonFirstName,
    Person.lastName AS otherPersonLastName,
    ct1 AS xCount,
    ct2 AS yCount,
    totalcount AS count
FROM
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
    ) friend,
    Person,
    City,
    Country,
    (
        SELECT
            chn.CreatorPersonId,
            ct1,
            ct2,
            ct1 + ct2 AS totalcount
        FROM
            (
                SELECT CreatorPersonId AS CreatorPersonId, count(*) AS ct1
                FROM Message, Country
                WHERE LocationCountryId = Country.id
                  AND Country.name = :countryXName
                  AND Message.creationDate >= :startDate
                  AND Message.creationDate < (:startDate + INTERVAL '1 days' * :durationDays)
                GROUP BY CreatorPersonId
            ) chn,
            (
                SELECT CreatorPersonId AS CreatorPersonId, count(*) AS ct2
                FROM Message, Country
                WHERE LocationCountryId = Country.id
                  AND Country.name = :countryYName
                  AND Message.creationDate >= :startDate
                  AND Message.creationDate < (:startDate + INTERVAL '1 days' * :durationDays)
                GROUP BY CreatorPersonId
            ) ind
        WHERE chn.CreatorPersonId = IND.CreatorPersonId
    ) cpc
WHERE friend.Person2Id = Person.id
  AND Person.LocationCityId = City.id
  AND City.PartOfCountryId = Country.id
  AND Country.name <> :countryXName
  AND Country.name <> :countryYName
  AND friend.Person2Id = cpc.CreatorPersonId
ORDER BY totalcount DESC, Person.id ASC
LIMIT 20
;
