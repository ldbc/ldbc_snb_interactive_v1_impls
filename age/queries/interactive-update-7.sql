SET search_path = ag_catalog, public;
SELECT * FROM cypher('$graphName', $$
  MATCH (author:Person {id: $authorPersonId}),
        (country:Country {id: $countryId}),
        (replyTo {id: $replyToId})
  CREATE (comment:Comment {
    id: $commentId,
    creationDate: $creationDate,
    locationIP: $locationIP,
    browserUsed: $browserUsed,
    content: $content,
    length: $length
  })-[:HAS_CREATOR]->(author),
  (comment)-[:REPLY_OF]->(replyTo),
  (comment)-[:IS_LOCATED_IN]->(country)
  WITH comment
  UNWIND $tagIds AS tagId
    MATCH (t:Tag {id: tagId})
    CREATE (comment)-[:HAS_TAG]->(t)
  RETURN count(comment)
$$) AS (result agtype);
