OPTIONAL MATCH (:Person {id: $personId})-[likes:LIKES]->(:Comment {id: $commentId})
WITH 1/count(likes) AS testWhetherLikesFound
// DEL 3: Remove comment like
MATCH (:Person {id: $personId})-[likes:LIKES]->(:Comment {id: $commentId})
DELETE likes
RETURN count(*)
