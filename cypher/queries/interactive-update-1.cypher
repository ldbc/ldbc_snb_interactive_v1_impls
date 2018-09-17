MATCH (c:City {id:$cityId})
CREATE (p:Person {id: $personId, firstName: $personFirstName, lastName: $personLastName, gender: $gender, birthday: $birthday, creationDate: $creationDate, locationIP: $locationIP, browserUsed: $browserUsed, speaks: $languages, emails: $emails})-[:IS_LOCATED_IN]->(c)
WITH p, count(*) AS dummy1
UNWIND $tagIds AS tagId
    MATCH (t:Tag)
    CREATE (p)-[:HAS_INTEREST]->(t)
WITH p, count(*) AS dummy2
UNWIND $studyAt AS s
    MATCH (u:Organisation {id: s.organizationId})
    CREATE (p)-[:STUDY_AT {classYear: s.year}]->(u)
WITH p, count(*) AS dummy3
UNWIND $workAt AS w
    MATCH (comp:Organisation {id: w.organizationId})
    CREATE (p)-[:WORKS_AT {workFrom: w.year}]->(comp)
