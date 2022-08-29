/* LdbcQuery1{personIdQ1=:personId, firstName=:firstName, limit=10}
 */
SELECT TOP(20)
    personId,
    lastName,
    min(distance) AS distance,
    birthday,
    creationDate,
    gender,
    browserUsed,
    locationIP,
    email as emails,
    language as languages,
    name as city
    , (SELECT string_agg(CONCAT(University.name , '|' , CONVERT(VARCHAR(max), Person_studyAt_University.classYear), '|' , City.name), ';')
        FROM Person_studyAt_University, University, City
        WHERE Person_studyAt_University.PersonId = Person.personId
          AND Person_studyAt_University.UniversityId = University.id
          AND University.LocationPlaceId = City.id
        GROUP BY personId
    ) AS university
    , (
    SELECT string_agg(CONCAT(Company.name , '|' , CONVERT(VARCHAR(max), Person_workAt_Company.workFrom), '|' , Country.name), ';')
        FROM Person_workAt_Company, Company, Country
        WHERE Person_workAt_Company.PersonId = Person.personId
          AND Person_workAt_Company.CompanyId = Company.id
          AND Company.LocationPlaceId = Country.id
        GROUP BY personId
    ) AS company
FROM
    (
        SELECT Person2Id AS id, 1 AS distance
        FROM Person_knows_Person, Person
        WHERE Person1Id = :personId
          AND personId = Person2Id
          AND firstName = N:firstName
        UNION ALL
        SELECT k2.Person2Id AS id, 2 AS distance
        FROM Person_knows_Person k1, Person_knows_Person k2, Person
        WHERE k1.Person1Id = :personId
          AND k2.Person1Id = k1.Person2Id
          AND personId = k2.Person2Id
          AND firstName = N:firstName
          AND Person.personId != :personId -- excluding start person
        UNION ALL
        SELECT k3.Person2Id AS id, 3 AS distance
        FROM Person_knows_Person k1, Person_knows_Person k2, Person_knows_Person k3, Person
        WHERE k1.Person1Id = :personId
          AND k2.Person1Id = k1.Person2Id
          AND k2.Person2Id = k3.Person1Id
          AND personId = k3.Person2Id
          AND firstName = N:firstName
          AND Person.personId != :personId -- excluding start person
    ) tmp, Person, City
  WHERE Person.personId = tmp.id
    AND Person.LocationCityId = City.id
  GROUP BY personId, lastName, birthday, creationDate, gender, browserUsed, locationIP, email, language, name
  ORDER BY distance, lastName, Person.personId
;
