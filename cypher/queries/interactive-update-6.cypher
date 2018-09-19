MATCH (author:Person {id: $authorPersonId}), (country:Country {id: $countryId}), (forum:Forum {id: $forumId})
WITH 
  CASE $content
    WHEN '' THEN null
    ELSE $content
  END AS realContent,
  CASE $imageFile
    WHEN '' THEN null
    ELSE $imageFile
  END AS realImageFile
CREATE (author)<-[:HAS_CREATOR]-(p:Post:Message {id: $postId, creationDate: $creationDate, locationIP: $locationIP, browserUsed: $browserUsed, content: realContent, imageFile: realImageFile, length: $length})<-[:CONTAINER_OF]-(forum), (p)-[:IS_LOCATED_IN]->(country)
WITH p
UNWIND $tagIds AS tagId
    MATCH (t:Tag {id: tagId})
    CREATE (p)-[:HAS_TAG]->(t)
