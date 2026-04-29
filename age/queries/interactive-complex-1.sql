SET search_path = ag_catalog, public;
SELECT friendId, friendLastName, distance, friendBirthday, friendCreationDate,
       friendGender, friendBrowserUsed, friendLocationIp, friendEmails, friendLanguages,
       friendCityName, friendUniversities, friendCompanies
FROM (
  SELECT DISTINCT ON (friendId)
    friendId, friendLastName, distance, friendBirthday, friendCreationDate,
    friendGender, friendBrowserUsed, friendLocationIp, friendEmails, friendLanguages,
    friendCityName, friendUniversities, friendCompanies
  FROM (
    SELECT * FROM cypher('$graphName', $$
      MATCH (p:Person {id: $personId})-[:KNOWS]->(friend:Person)-[:IS_LOCATED_IN]->(city:City)
      WHERE friend.firstName = $firstName AND friend.id <> $personId
      OPTIONAL MATCH (friend)-[studyAt:STUDY_AT]->(uni:University)-[:IS_LOCATED_IN]->(uniCity:City)
      WITH friend, city, collect(CASE WHEN uni IS NULL THEN null ELSE [uni.name, studyAt.classYear, uniCity.name] END) AS unis
      OPTIONAL MATCH (friend)-[workAt:WORK_AT]->(company:Company)-[:IS_LOCATED_IN]->(compCountry:Country)
      WITH friend, city, unis, collect(CASE WHEN company IS NULL THEN null ELSE [company.name, workAt.workFrom, compCountry.name] END) AS companies
      RETURN friend.id, friend.lastName, 1, friend.birthday, friend.creationDate,
             friend.gender, friend.browserUsed, friend.locationIP, friend.email, friend.speaks,
             city.name, unis, companies
    $$) AS (friendId agtype, friendLastName agtype, distance agtype, friendBirthday agtype,
            friendCreationDate agtype, friendGender agtype, friendBrowserUsed agtype,
            friendLocationIp agtype, friendEmails agtype, friendLanguages agtype,
            friendCityName agtype, friendUniversities agtype, friendCompanies agtype)
    UNION ALL
    SELECT * FROM cypher('$graphName', $$
      MATCH (p:Person {id: $personId})-[:KNOWS]->(:Person)-[:KNOWS]->(friend:Person)-[:IS_LOCATED_IN]->(city:City)
      WHERE friend.firstName = $firstName AND friend.id <> $personId
      OPTIONAL MATCH (friend)-[studyAt:STUDY_AT]->(uni:University)-[:IS_LOCATED_IN]->(uniCity:City)
      WITH friend, city, collect(CASE WHEN uni IS NULL THEN null ELSE [uni.name, studyAt.classYear, uniCity.name] END) AS unis
      OPTIONAL MATCH (friend)-[workAt:WORK_AT]->(company:Company)-[:IS_LOCATED_IN]->(compCountry:Country)
      WITH friend, city, unis, collect(CASE WHEN company IS NULL THEN null ELSE [company.name, workAt.workFrom, compCountry.name] END) AS companies
      RETURN friend.id, friend.lastName, 2, friend.birthday, friend.creationDate,
             friend.gender, friend.browserUsed, friend.locationIP, friend.email, friend.speaks,
             city.name, unis, companies
    $$) AS (friendId agtype, friendLastName agtype, distance agtype, friendBirthday agtype,
            friendCreationDate agtype, friendGender agtype, friendBrowserUsed agtype,
            friendLocationIp agtype, friendEmails agtype, friendLanguages agtype,
            friendCityName agtype, friendUniversities agtype, friendCompanies agtype)
    UNION ALL
    SELECT * FROM cypher('$graphName', $$
      MATCH (p:Person {id: $personId})-[:KNOWS]->(:Person)-[:KNOWS]->(:Person)-[:KNOWS]->(friend:Person)-[:IS_LOCATED_IN]->(city:City)
      WHERE friend.firstName = $firstName AND friend.id <> $personId
      OPTIONAL MATCH (friend)-[studyAt:STUDY_AT]->(uni:University)-[:IS_LOCATED_IN]->(uniCity:City)
      WITH friend, city, collect(CASE WHEN uni IS NULL THEN null ELSE [uni.name, studyAt.classYear, uniCity.name] END) AS unis
      OPTIONAL MATCH (friend)-[workAt:WORK_AT]->(company:Company)-[:IS_LOCATED_IN]->(compCountry:Country)
      WITH friend, city, unis, collect(CASE WHEN company IS NULL THEN null ELSE [company.name, workAt.workFrom, compCountry.name] END) AS companies
      RETURN friend.id, friend.lastName, 3, friend.birthday, friend.creationDate,
             friend.gender, friend.browserUsed, friend.locationIP, friend.email, friend.speaks,
             city.name, unis, companies
    $$) AS (friendId agtype, friendLastName agtype, distance agtype, friendBirthday agtype,
            friendCreationDate agtype, friendGender agtype, friendBrowserUsed agtype,
            friendLocationIp agtype, friendEmails agtype, friendLanguages agtype,
            friendCityName agtype, friendUniversities agtype, friendCompanies agtype)
  ) all_hops
  ORDER BY friendId, distance, friendLastName
) deduped
ORDER BY distance, friendLastName, friendId
LIMIT 20;
