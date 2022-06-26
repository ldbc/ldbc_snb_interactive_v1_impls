/* Q13. Single shortest path
\set person1Id 17592186044461
\set person2Id 15393162788877
 */
WITH RECURSIVE search_graph(link, depth, path) AS (
    SELECT :Person1Id::bigint, 0, ARRAY[:Person1Id::bigint]::bigint[]
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
            AND NOT EXISTS (SELECT * FROM sg y WHERE y.link = :Person2Id::bigint)
            -- skip reaching Persons reached in the previous iteration
            AND NOT EXISTS (SELECT * FROM sg y WHERE y.link = Person2Id)
      )
)
SELECT max(depth)
FROM
    (
        SELECT depth
        FROM search_graph
        WHERE link = :Person2Id::bigint
        UNION
        SELECT -1
    ) tmp;
;
