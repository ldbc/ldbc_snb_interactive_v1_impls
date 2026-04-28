SET search_path = ag_catalog, public;
SELECT * FROM cypher('$graphName', $$
  MATCH (author:Person {id: $authorPersonId}),
        (country:Country {id: $countryId}),
        (forum:Forum {id: $forumId})
  CREATE (post:Post {
    id: $postId,
    creationDate: $creationDate,
    locationIP: $locationIP,
    browserUsed: $browserUsed,
    language: $language,
    content: CASE $content WHEN '' THEN null ELSE $content END,
    imageFile: CASE $imageFile WHEN '' THEN null ELSE $imageFile END,
    length: $length
  })-[:HAS_CREATOR]->(author),
  (forum)-[:CONTAINER_OF]->(post),
  (post)-[:IS_LOCATED_IN]->(country)
  WITH post
  UNWIND $tagIds AS tagId
    MATCH (t:Tag {id: tagId})
    CREATE (post)-[:HAS_TAG]->(t)
  RETURN count(post)
$$) AS (result agtype);
