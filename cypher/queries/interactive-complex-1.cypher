MATCH (:Person {id:{1}})-[path:KNOWS*1..3]-(friend:Person)
WHERE friend.firstName = {2}
WITH friend, min(length(path)) AS distance
ORDER BY distance ASC, friend.lastName ASC, toInt(friend.id) ASC
LIMIT {3}
MATCH (friend)-[:IS_LOCATED_IN]->(friendCity:Place)
OPTIONAL MATCH (friend)-[studyAt:STUDY_AT]->(uni:Organisation)-[:IS_LOCATED_IN]->(uniCity:Place)
WITH
  friend,
  collect(
    CASE uni.name
      WHEN null THEN null
      ELSE [uni.name, studyAt.classYear, uniCity.name]
    END
  ) AS unis,
  friendCity,
  distance
OPTIONAL MATCH (friend)-[worksAt:WORK_AT]->(company:Organisation)-[:IS_LOCATED_IN]->(companyCountry:Place)
WITH
  friend,
  collect(
    CASE company.name
      WHEN null THEN null
      ELSE [company.name, worksAt.workFrom, companyCountry.name]
    END
  ) AS companies,
  unis,
  friendCity,
  distance
RETURN
  friend.id AS id,
  friend.lastName AS lastName,
  distance,
  friend.birthday AS birthday,
  friend.creationDate AS creationDate,
  friend.gender AS gender,
  friend.browserUsed AS browser,
  friend.locationIP AS locationIp,
  friend.email AS emails,
  friend.speaks AS languages,
  friendCity.name AS cityName,
  unis,
  companies
ORDER BY distance ASC, friend.lastName ASC, toInt(friend.id) ASC
LIMIT {3};
