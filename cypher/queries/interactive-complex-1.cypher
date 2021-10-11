// Q1. Transitive friends with certain name
/*
:param [{ personId, firstName }] => { RETURN
  4398046511333 AS personId,
  "Jose" AS firstName
}
*/
MATCH p=shortestPath((person:Person {id: $personId})-[path:KNOWS*1..3]-(friend:Person {firstName: $firstName}))
WHERE person <> friend
WITH friend, length(p) AS distance
  ORDER BY distance ASC, friend.lastName ASC, friend.id ASC
  LIMIT 20
MATCH (friend)-[:IS_LOCATED_IN]->(friendCity:City)
OPTIONAL MATCH (friend)-[studyAt:STUDY_AT]->(uni:Organisation)-[:IS_LOCATED_IN]->(uniCity:City)
WITH
  friend,
  collect(
    CASE uni.name
      WHEN NULL THEN NULL
      ELSE [uni.name, studyAt.classYear, uniCity.name]
      END
  ) AS unis,
  friendCity,
  distance
OPTIONAL MATCH (friend)-[workAt:WORK_AT]->(company:Organisation)-[:IS_LOCATED_IN]->(companyCountry:Country)
WITH
  friend,
  collect(
    CASE company.name
      WHEN NULL THEN NULL
      ELSE [company.name, workAt.workFrom, companyCountry.name]
      END
  ) AS companies,
  unis,
  friendCity,
  distance
RETURN
  friend.id AS friendId,
  friend.lastName AS friendLastName,
  distance AS distanceFromPerson,
  friend.birthday AS friendBirthday,
  friend.creationDate AS friendCreationDate,
  friend.gender AS friendGender,
  friend.browserUsed AS friendBrowserUsed,
  friend.locationIP AS friendLocationIp,
  friend.email AS friendEmails,
  friend.speaks AS friendLanguages,
  friendCity.name AS friendCityName,
  unis AS friendUniversities,
  companies AS friendCompanies
  ORDER BY distanceFromPerson ASC, friendLastName ASC, friendId ASC
  LIMIT 20
