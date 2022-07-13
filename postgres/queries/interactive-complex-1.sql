/* Q1. Transitive friends WITH certain name
\set personId 17592186044461
\set firstName '\'Jose\''
 */
SELECT
    Person.id,
    Person.lastName,
    min(distance) AS distance,
    Person.birthday,
    Person.creationDate,
    Person.gender,
    Person.browserUsed,
    Person.locationIP,
    string_to_array(Person.email, ';') AS emails,
    string_to_array(Person.speaks, ';') AS languages,
    City.name,
    (
        SELECT array_agg(ARRAY[University.name, Person_studyAt_University.classYear::text, City.name])
        FROM Person_studyAt_University, University, City
        WHERE Person_studyAt_University.PersonId = Person.id
          AND Person_studyAt_University.UniversityId = University.id
          AND University.LocationPlaceId = City.id
        GROUP BY Person.id
    ) AS university,
    (
        SELECT array_agg(ARRAY[Company.name, Person_workAt_Company.workFrom::text, Country.name])
        FROM Person_workAt_Company, Company, Country
        WHERE Person_workAt_Company.PersonId = Person.id
          AND Person_workAt_Company.CompanyId = Company.id
          AND Company.LocationPlaceId = Country.id
        GROUP BY Person.id
    ) AS company
FROM
    (
        SELECT Person2Id AS id, 1 AS distance
        FROM Person_knows_Person, Person
        WHERE Person1Id = :personId
          AND Person.id = Person2Id
          AND firstName = :firstName
        UNION ALL
        SELECT k2.Person2Id AS id, 2 AS distance
        FROM Person_knows_Person k1, Person_knows_Person k2, Person
        WHERE k1.Person1Id = :personId
          AND k2.Person1Id = k1.Person2Id
          AND Person.id = k2.Person2Id
          AND firstName = :firstName
          AND Person.id != :personId -- excluding start person
        UNION ALL
        SELECT k3.Person2Id AS id, 3 AS distance
        FROM Person_knows_Person k1, Person_knows_Person k2, Person_knows_Person k3, Person
        WHERE k1.Person1Id = :personId
          AND k2.Person1Id = k1.Person2Id
          AND k2.Person2Id = k3.Person1Id
          AND Person.id = k3.Person2Id
          AND firstName = :firstName
          AND Person.id != :personId -- excluding start person
    ) tmp, Person, City
  WHERE Person.id = tmp.id
    AND Person.LocationCityId = City.id
  GROUP BY Person.id, lastName, birthday, creationDate, gender, browserUsed, locationIP, City.name
  ORDER BY distance, lastName, Person.id
  LIMIT 20
;
