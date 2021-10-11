// Q4. New topics
/*
:param [{ personId, startDate, durationDays }] => { RETURN
  10995116277918 AS personId,
  "2010-10-01" AS startDate,
  31 AS durationDays
}
*/
MATCH (person:Person {id: $personId})-[:KNOWS]-(:Person)<-[:HAS_CREATOR]-(post:Post)-[:HAS_TAG]->(tag:Tag)
WHERE post.creationDate >= datetime($startDate)
  AND post.creationDate < datetime($startDate) + duration({days: $durationDays})
WITH person, count(post) AS postsOnTag, tag
OPTIONAL MATCH (person)-[:KNOWS]-()<-[:HAS_CREATOR]-(oldPost:Post)-[:HAS_TAG]->(tag)
         WHERE oldPost.creationDate < datetime($startDate)
WITH person, postsOnTag, tag, count(oldPost) AS numberOfOldPosts
WHERE numberOfOldPosts = 0
RETURN
  tag.name AS tagName,
  sum(postsOnTag) AS postCount
ORDER BY postCount DESC, tagName ASC
LIMIT 10
