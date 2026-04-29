SET search_path = ag_catalog, public;
SELECT * FROM cypher('$graphName', $$
  MATCH (city:City {id: $cityId})
  CREATE (p:Person {
    id: $personId,
    firstName: $personFirstName,
    lastName: $personLastName,
    gender: $gender,
    birthday: $birthday,
    creationDate: $creationDate,
    locationIP: $locationIP,
    browserUsed: $browserUsed,
    speaks: $languages,
    email: $emails
  })-[:IS_LOCATED_IN]->(city)
  WITH p, count(*) AS dummy1
  UNWIND $tagIds AS tagId
    MATCH (t:Tag {id: tagId})
    CREATE (p)-[:HAS_INTEREST]->(t)
  WITH p, count(*) AS dummy2
  UNWIND $studyAt AS s
    MATCH (u:University {id: s.organizationId})
    CREATE (p)-[:STUDY_AT {classYear: s.year}]->(u)
  WITH p, count(*) AS dummy3
  UNWIND $workAt AS w
    MATCH (comp:Company {id: w.organizationId})
    CREATE (p)-[:WORK_AT {workFrom: w.year}]->(comp)
  RETURN count(*)
$$) AS (result agtype);
