MATCH (person:Person {id:{1}})-[:KNOWS*1..2]-(friend:Person)
WHERE not(person=friend)
WITH DISTINCT friend
MATCH (friend)-[worksAt:WORK_AT]->(company:Organisation)-[:IS_LOCATED_IN]->(:Place {name:{3}})
WHERE worksAt.workFrom < {2}
RETURN
  friend.id AS friendId,
  friend.firstName AS friendFirstName,
  friend.lastName AS friendLastName,
  company.name AS companyName,
  worksAt.workFrom AS workFromYear
ORDER BY workFromYear ASC, toInt(friendId) ASC, companyName DESC
LIMIT {4};
