MATCH (person:Person {id:{1}})-[:KNOWS]-(:Person)<-[:HAS_CREATOR]-(post:Post)-[:HAS_TAG]->(tag:Tag)
WHERE post.creationDate >= {2} AND post.creationDate < {3}
OPTIONAL MATCH (tag)<-[:HAS_TAG]-(oldPost:Post)-[:HAS_CREATOR]->(:Person)-[:KNOWS]-(person)
WHERE oldPost.creationDate < {2}
WITH tag, post, length(collect(oldPost)) AS oldPostCount
WHERE oldPostCount=0
RETURN
  tag.name AS tagName,
  length(collect(post)) AS postCount
ORDER BY postCount DESC, tagName ASC
LIMIT {4};
