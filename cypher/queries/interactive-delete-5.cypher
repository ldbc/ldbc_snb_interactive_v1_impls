OPTIONAL MATCH (:Forum {id: $forumId})-[hasMember:HAS_MEMBER]->(:Person {id: $personId})
WITH 1/count(hasMember) AS testWhetherHasMemberFound
// DEL 5: Remove forum membership
MATCH (:Forum {id: $forumId})-[hasMember:HAS_MEMBER]->(:Person {id: $personId})
DELETE hasMember
RETURN count(*)
