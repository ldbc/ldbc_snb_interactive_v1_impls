// DEL 2: Remove post like
MATCH (:Person {id: $personId})-[likes:LIKES]->(:Post {id: $postId})
DELETE likes
RETURN count(*)
