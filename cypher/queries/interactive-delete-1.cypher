// DEL 1: Remove person and its personal forums and message (sub)threads
MATCH (person:Person {id: $personId})
// DEL 6/7: Post/Comment
OPTIONAL MATCH (person)<-[:HAS_CREATOR]-(:Message)<-[:REPLY_OF*0..]-(message1:Message)
// DEL 4: Forum
OPTIONAL MATCH (person)<-[:HAS_MODERATOR]-(forum:Forum)
WHERE forum.title STARTS WITH 'Album '
   OR forum.title STARTS WITH 'Wall '
OPTIONAL MATCH (forum)-[:CONTAINER_OF]->(:Post)<-[:REPLY_OF*0..]-(message2:Message)
DETACH DELETE person, forum, message1, message2
RETURN count(*)
