/* Q13. Single shortest path
\set person1Id 17592186044461
\set person2Id 15393162788877
 */
WITH RECURSIVE search_graph(link, depth, path) AS (
    SELECT :person1Id::bigint, 0, ARRAY[:person1Id::bigint]::bigint[]
    UNION ALL
        (
          WITH
              sg(link, depth) AS (SELECT * FROM search_graph) -- Note: sg is only the diff produced in the previous iteration
          SELECT DISTINCT
              Person2Id, x.depth+1, array_append(path, Person2Id)
          FROM Person_knows_Person, sg x
          WHERE x.link = Person1Id
            AND Person2Id <> ALL (path)
            -- stop if we have reached Person2 in the previous iteration
            AND NOT EXISTS (SELECT * FROM sg y WHERE y.link = :person2Id::bigint)
            -- skip reaching Persons reached in the previous iteration
            AND NOT EXISTS (SELECT * FROM sg y WHERE y.link = Person2Id)
      )
)
SELECT max(depth)
FROM
    (
        SELECT depth
        FROM search_graph
        WHERE link = :person2Id::bigint
        UNION
        SELECT -1
    ) tmp;
;
