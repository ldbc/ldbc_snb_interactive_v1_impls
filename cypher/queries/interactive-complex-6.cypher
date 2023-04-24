// Q6. Tag co-occurrence
/*
:params { personId: 4398046511333, tagName: "Carl_Gustaf_Emil_Mannerheim" }
*/
MATCH (knownTag:Tag { name: $tagName })
WITH knownTag.id as knownTagId

MATCH (person:Person { id: $personId })-[:KNOWS*1..2]-(friend)
WHERE NOT person=friend
WITH
    knownTagId,
    collect(distinct friend) as friends
UNWIND friends as f
    MATCH (f)<-[:HAS_CREATOR]-(post:Post),
          (post)-[:HAS_TAG]->(t:Tag{id: knownTagId}),
          (post)-[:HAS_TAG]->(tag:Tag)
    WHERE NOT t = tag
    WITH
        tag.name as tagName,
        count(post) as postCount
RETURN
    tagName,
    postCount
ORDER BY
    postCount DESC,
    tagName ASC
LIMIT 10
