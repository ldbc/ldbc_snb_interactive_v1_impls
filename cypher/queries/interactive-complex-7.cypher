// Q7. Recent likers
/*
:param personId: 4398046511268
*/
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
  duration.inSeconds(latestLike.msg.creationDate, latestLike.likeTime).minutes AS minutesLatency,
  NOT (liker)-[:KNOWS]-(person) AS isNew
ORDER BY likeCreationDate DESC, personId ASC
LIMIT 20
;
