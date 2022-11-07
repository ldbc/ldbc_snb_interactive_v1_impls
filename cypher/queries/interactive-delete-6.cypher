OPTIONAL MATCH (post:Post {id: $postId})
WITH 1/count(post) AS testWhetherRootPostFound
// DEL 6: Remove post thread
MATCH (:Post {id: $postId})<-[:REPLY_OF*0..]-(message:Message) // DEL 6/7
DETACH DELETE message
RETURN count(*)
