SELECT TOP(20)
                Person.id,
                Person.lastName,
                min(distance) AS distance,
                Person.birthday,
                Person.creationDate,
                Person.gender,
                Person.browserUsed,
                Person.locationIP,
                Person.email AS emails,
                Person.speaks AS languages,
                City.name,
             , (SELECT string_agg(CONCAT(University.name , '|' , CONVERT(VARCHAR(max), Person_studyAt_University.classYear), '|' , City.name), ';')
                    FROM Person_studyAt_University, University, City
                    WHERE Person_studyAt_University.PersonId = Person.id
                    AND Person_studyAt_University.UniversityId = University.id
                    AND University.LocationPlaceId = City.id
                    GROUP BY Person.id
                 GROUP BY Person.id
               ) AS university
             , (
                SELECT string_agg(CONCAT(Company.name , '|' , CONVERT(VARCHAR(max), Person_workAt_Company.workFrom), '|' , Country.name), ';')
                    FROM Person_workAt_Company, Company, Country
                    WHERE Person_workAt_Company.PersonId = Person.id
                    AND Person_workAt_Company.CompanyId = Company.id
                    AND Company.LocationPlaceId = Country.id
                    GROUP BY Person.id
                ) AS company
  FROM (SELECT Person2Id AS id
             , 1 AS dist FROM Person_knows_Person
             , person 
         WHERE Person1Id = :personId
           AND Person.id = Person2Id 
           AND firstName = :firstName
         UNION ALL
        SELECT b.Person2Id AS id
             , 2 AS dist 
          FROM Person_knows_Person a
             , Person_knows_Person b
             , Person
         WHERE a.Person1Id = :personId
           AND b.Person1Id = a.Person2Id
           AND Person.id = b.Person2Id
           AND firstName = :firstName
           AND Person.id != :personId -- excluding start person
         UNION ALL
        SELECT c.Person2Id AS id
             , 3 AS dist
          FROM Person_knows_Person a
             , Person_knows_Person b
             , Person_knows_Person c
             , person
         WHERE a.Person1Id = :personId
           AND b.Person1Id = a.Person2Id
           AND b.Person2Id = c.Person1Id
           AND Person.id = c.Person2Id
           AND firstName = :firstName
           AND Person.id != :personId -- excluding start person
  ) tmp, person, place p1
  WHERE Person.id = tmp.id
    AND Person.LocationCityId = City.id
  GROUP BY Person.id, lastName, birthday, creationDate, gender, browserUsed, locationIP, City.name
  ORDER BY distance, lastName, Person.id
;
