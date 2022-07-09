/* Q14. Trusted connection paths
\set person1Id 17592186044461
\set person2Id 15393162788877
 */
WITH start_node(v) AS (
    SELECT :person1Id::bigint
)
SELECT *
FROM
    (
        WITH RECURSIVE
            search_graph(link, depth, path) AS (
                SELECT v::bigint, 0, ARRAY[]::bigint[][] FROM start_node
                UNION ALL (
                    WITH sg(link, depth) AS (SELECT * FROM search_graph)
                    SELECT DISTINCT Person2Id, x.depth + 1, path || ARRAY[[x.link, Person2Id]]
                    FROM Person_knows_Person, sg x
                    WHERE x.link = Person1Id
                    AND NOT EXISTS (SELECT * FROM sg y WHERE y.link = :person2Id::bigint)
                    AND NOT EXISTS (SELECT * FROM sg y WHERE y.link = Person2Id)
                )
        ),
        paths(pid, path) AS (
            SELECT row_number() OVER (), path
            FROM search_graph
            WHERE link = :person2Id::bigint
        ),
        edges(id, e) AS (
            SELECT pid, array_agg(path[d1][d2])
            FROM paths, generate_subscripts(path, 1) d1, generate_subscripts(path, 2) d2
            GROUP BY pid, d1
        ),
        unique_edges(e) AS (
            SELECT DISTINCT e
            FROM edges
        ),
        weights(we, score) AS (
            SELECT e, sum(score)
            FROM (
                SELECT e, mid1, mid2, max(score) AS score
                FROM (
                    SELECT e, 1 AS score, m1.id AS mid1, m2.id AS mid2
                    FROM unique_edges, Message m1, Message m2
                    WHERE (m1.CreatorPersonId = e[1] AND m2.CreatorPersonId = e[2] AND m2.ParentMessageId = m1.id AND m1.ParentMessageId IS NULL)
                    UNION ALL
                    SELECT e, 1 AS score, m1.id AS mid1, m2.id AS mid2
                    FROM unique_edges, Message m1, Message m2
                    WHERE (m1.CreatorPersonId = e[2] AND m2.CreatorPersonId = e[1] AND m2.ParentMessageId = m1.id AND m1.ParentMessageId IS NULL)
                    UNION ALL
                    SELECT e, 0.5 AS score, m1.id AS mid1, m2.id AS mid2
                    FROM unique_edges, Message m1, Message m2
                    WHERE (m1.CreatorPersonId = e[1] AND m2.CreatorPersonId = e[2] AND m2.ParentMessageId = m1.id AND m1.ParentMessageId IS NOT NULL)
                    UNION ALL
                    SELECT e, 0.5 AS score, m1.id AS mid1, m2.id AS mid2
                    FROM unique_edges, Message m1, Message m2
                    WHERE (m1.CreatorPersonId = e[2] AND m2.CreatorPersonId = e[1] AND m2.ParentMessageId = m1.id AND m1.ParentMessageId IS NOT NULL)
                ) pps
                GROUP BY e, mid1, mid2
            ) tmp
            GROUP BY e
        ),
        weightedpaths(path, score) AS (
            SELECT path, coalesce(sum(score), 0)
            FROM paths, edges
            LEFT JOIN weights
                   ON we = e
            WHERE pid = id
            GROUP BY id, path
        )
        SELECT path, score
        FROM weightedpaths
        ORDER BY score DESC
    ) x
ORDER BY score DESC;
;
