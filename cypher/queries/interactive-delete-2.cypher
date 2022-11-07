OPTIONAL MATCH (:Person {id: $personId})-[likes:LIKES]->(:Post {id: $postId})
WITH 1/count(likes) AS testWhetherLikesFound
// DEL 2: Remove post like
MATCH (:Person {id: $personId})-[likes:LIKES]->(:Post {id: $postId})
DELETE likes
RETURN count(*)
