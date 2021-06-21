MATCH (person:Person {id: $personId})<-[:HAS_CREATOR]-(message)<-[like:LIKES]-(liker:Person)
WITH liker, message, like.creationDate AS likeTime, person
ORDER BY likeTime DESC, message.id ASC
WITH
  liker,
  collect([message, likeTime]) AS latestLikes,
  person
WITH
  liker,
  head(latestLikes) AS latestLike,
  person
RETURN
  liker.id AS personId,
  liker.firstName AS personFirstName,
  liker.lastName AS personLastName,
  latestLike[1] AS likeCreationDate,
  latestLike[0].id AS messageId,
  coalesce(latestLike[0].content, latestLike[0].imageFile) AS messageContent,
  latestLike[0].creationDate AS messageCreationDate,
  not((liker)-[:KNOWS]-(person)) AS isNew
ORDER BY likeCreationDate DESC, personId ASC
LIMIT 20
