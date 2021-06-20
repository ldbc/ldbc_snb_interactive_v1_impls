MATCH (person:Person {id: $personId})<-[:HAS_CREATOR]-(message:Message)<-[like:LIKES]-(liker:Person)
WITH liker, message, like.creationDate AS likeTime, person
ORDER BY likeTime DESC, message.id ASC
WITH
  liker,
  head(collect({msg: message, likeTime: likeTime})) AS latestLike,
  person
RETURN
  liker.id AS personId,
  liker.firstName AS personFirstName,
  liker.lastName AS personLastName,
  latestLike.likeTime AS likeCreationDate,
  latestLike.msg.id AS messageId,
  coalesce(latestLike.msg.content, latestLike.msg.imageFile) AS messageContent,
  latestLike.likeTime.minute - latestLike.msg.creationDate.minute AS minutesLatency,
  NOT (liker)-[:KNOWS]-(person) AS isNew
ORDER BY likeCreationDate DESC, personId ASC
LIMIT 20
