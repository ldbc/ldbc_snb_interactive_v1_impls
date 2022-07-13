// DEL 4: Remove forum and its content
MATCH (forum:Forum {id: $forumId})
OPTIONAL MATCH (forum)-[:CONTAINER_OF]->(:Post)<-[:REPLY_OF*0..]-(message:Message)
DETACH DELETE forum, message
RETURN count(*)
