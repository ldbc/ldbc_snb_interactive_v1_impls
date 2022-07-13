// DEL 5: Remove forum membership
MATCH (:Forum {id: $forumId})-[hasMember:HAS_MEMBER]->(:Person {id: $personId})
DELETE hasMember
RETURN count(*)
