// IS1. Profile of a person
/*
:param personId: 10995116277794
 */
MATCH (person:Person {id: $personId})-[:IS_LOCATED_IN]->(city:City)
RETURN
  person.firstName AS firstName,
  person.lastName AS lastName,
  person.birthday AS birthday,
  person.locationIP AS locationIP,
  person.browserUsed AS browserUsed,
  city.id AS cityId,
  person.gender AS gender,
  person.creationDate AS creationDate
