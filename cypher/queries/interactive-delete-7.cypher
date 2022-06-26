// DEL 7: Remove comment subthread
MATCH (:Comment {id: $commentId})<-[:REPLY_OF*0..]-(comment:Comment)
DETACH DELETE comment
RETURN count(*)
