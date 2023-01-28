// Q9. Recent messages by friends or friends of friends
/*
:param [{ personId, maxDate }] => { RETURN
  4398046511268 AS personId,
  1289908800000 AS maxDate
}
*/
MATCH (Pa:Person {id: $personId })-[:KNOWS*1..2]-(Pb:Person)<-[:HAS_CREATOR]-(m:Message)
WHERE Pa <> Pb
  AND m.creationDate < $maxDate
RETURN DISTINCT
  Pb.id AS personId,
  Pb.firstName AS personFirstName,
  Pb.lastName AS personLastName,
  m.id AS commentOrPostId,
  coalesce(m.content, m.imageFile) AS commentOrPostContent,
  m.creationDate AS commentOrPostCreationDate
ORDER BY
  commentOrPostCreationDate DESC,
  m.id ASC
LIMIT 20
