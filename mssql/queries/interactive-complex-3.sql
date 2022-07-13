SELECT TOP(20)
            Person.personId AS otherPersonId,
            Person.firstName AS otherPersonFirstName,
            Person.lastName AS otherPersonLastName,
            ct1 AS xCount,
            ct2 AS yCount,
            total AS count
          FROM (SELECT Person2Id
                  FROM Person_knows_Person
                 WHERE Person1Id = :personId
                 UNION
                SELECT k2.Person2Id
                  FROM Person_knows_Person k1
                     , Person_knows_Person k2
                 WHERE k1.Person1Id = :personId
                   AND k1.Person2Id = k2.Person1Id
                   AND k2.Person2Id <> :personId
               ) friend,
                Person,
                City,
                Country,
               (
                SELECT chn.CreatorPersonId
                     , ct1
                     , ct2
                     , ct1 + ct2 AS total
                  FROM
                     (
                SELECT CreatorPersonId, count(*) AS ct1
                FROM Message, Country
                 WHERE LocationCountryId = Country.id
                  AND Country.name = :countryXName
                  AND Message.creationDate >= :startDate
                   AND creationDate < DATEADD(day, :durationDays, :startDate)
              GROUP BY CreatorPersonId
                     ) chn
                     , (SELECT CreatorPersonId, count(*) AS ct2
                          FROM Message, Country
                         WHERE LocationCountryId = Country.id
                            AND Country.name = :countryYName
                            AND Message.creationDate >= :startDate
                           AND Message.creationDate < DATEADD(day, :durationDays, :startDate)
                      GROUP BY CreatorPersonId
                             ) ind
                WHERE chn.CreatorPersonId = IND.CreatorPersonId
                    ) cpc
            WHERE friend.Person2Id = Person.personId
            AND Person.LocationCityId = City.id
            AND City.PartOfCountryId = Country.id
            AND Country.name <> :countryXName
            AND Country.name <> :countryYName
            AND friend.Person2Id = cpc.CreatorPersonId
            ORDER BY total DESC, Person.personId ASC
;
