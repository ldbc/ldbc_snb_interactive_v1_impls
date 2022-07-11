// DEL 3: Remove comment like
MATCH (:Person {id: $personId})-[likes:LIKES]->(:Comment {id: $commentId})
DELETE likes
RETURN count(*)
