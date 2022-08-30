/* Q13. Single shortest path
\set person1Id 17592186044461
\set person2Id 15393162788877
 */
WITH RECURSIVE
-- Bidirectional breadth first search
bfs(dir, node, depth) AS (
    SELECT dd,nn,ll FROM (VALUES (false, :person1Id::bigint, 0), (true, :person2Id::bigint, 0)) t(dd,nn,ll)
    UNION ALL
    (
        with
        rec AS (SELECT dir, node, depth FROM bfs),
        md AS (SELECT max(depth) AS d FROM rec),
        last AS (SELECT dir, node, depth FROM rec WHERE depth = (SELECT d FROM md)),
        new AS (
            SELECT dir, pkp.person2id AS dst, min(depth) + 1 AS md
            FROM last, person_knows_person pkp
            WHERE last.node = pkp.person1id AND pkp.person2id not in (SELECT node FROM rec WHERE last.dir = rec.dir)
            group by dir, pkp.person2id
        )
        SELECT dir, dst, md
        FROM
            (SELECT dir, dst, md FROM new
            union all
            SELECT dir, node, depth
            FROM rec) t
        WHERE true 
            AND EXISTS (SELECT * FROM new)
            AND NOT EXISTS (
                    SELECT *
                    FROM last r1, rec r2
                    WHERE r1.dir = true AND r2.dir = false AND r1.node = r2.node
                )

    )
),
md AS (SELECT max(depth) AS d FROM bfs),
found(depth) AS (
    SELECT min(r1.depth + r2.depth)
    FROM bfs r1, bfs r2
    WHERE r1.depth = (SELECT d FROM md) and
          r1.dir = true AND r2.dir = false AND r1.node = r2.node

)
SELECT coalesce((SELECT depth FROM found), -1)
;
