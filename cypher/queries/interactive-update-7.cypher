MATCH
  (author:Person {id: $authorPersonId}),
  (country:Country {id: $countryId}),
  (message:Message {id: CASE $replyToPostId WHEN -1 THEN $replyToCommentId ELSE $replyToPostId END})
CREATE (author)<-[:HAS_CREATOR]-(c:Comment:Message {id: $commentId, creationDate: $creationDate, locationIP: $locationIP, browserUsed: $browserUsed, content: $content, length: $length})-[:REPLY_OF]->(message), (c)-[:IS_LOCATED_IN]->(country)
WITH c
UNWIND $tagIds AS tagId
    MATCH (t:Tag {id: tagId})
    CREATE (c)-[:HAS_TAG]->(t)
