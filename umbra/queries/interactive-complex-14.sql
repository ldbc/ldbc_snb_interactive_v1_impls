/* Q14. Trusted connection paths
\set person1Id 17592186044461
\set person2Id 15393162788877
 */
with recursive
pathb(a, b, w) AS (
    SELECT least(c.creatorpersonid, p.creatorpersonid) AS a, greatest(c.creatorpersonid, p.creatorpersonid) AS b, greatest(round(40 - sqrt(count(*)))::bigint, 1)  AS w
    FROM message c, message p
    WHERE c.parentmessageid = p.id AND EXISTS (SELECT * FROM person_knows_person WHERE person1id = c.creatorpersonid AND person2id = p.creatorpersonid)
    group by a, b
),
path(src, dst, w) AS (
    SELECT a, b, w
    FROM pathb
    union all
    SELECT b, a, w
    FROM pathb
),
shorts(dir, gsrc, dst, prev, w, dead, iter) AS (
    SELECT sdir, sgsrc, sdst, sdst, sw, sdead, siter
    FROM
        (VALUES
             (false, :person1Id::bigint, :person1Id::bigint, 0::bigint, false, 0),
             (true, :person2Id::bigint, :person2Id::bigint, 0::bigint, false, 0))
        t(sdir, sgsrc, sdst, sw, sdead, siter)
    union all
    (
        with
        ss AS (SELECT * FROM shorts),
        toExplore AS (SELECT * FROM ss WHERE dead = false order by w limit 1000),
        -- assumes graph is undirected
        newPoints(dir, gsrc, dst, prev, w, dead) AS (
            SELECT e.dir, e.gsrc AS gsrc, p.dst AS dst, p.src as prev, e.w + p.w AS w, false AS dead
            FROM path p join toExplore e on (e.dst = p.src)
            UNION ALL
            SELECT dir, gsrc, dst, prev, w, dead OR EXISTS (SELECT * FROM toExplore e WHERE e.dir = o.dir AND e.gsrc = o.gsrc AND e.dst = o.dst) FROM ss o
        ),
        fullTable AS (
            SELECT DISTINCT ON(dir, gsrc, dst) dir, gsrc, dst, prev, w, dead
            FROM newPoints
            ORDER BY dir, gsrc, dst, w, dead, prev DESC
        ),
        found AS (
            SELECT min(l.w + r.w) AS w
            FROM fullTable l, fullTable r
            WHERE l.dir = false AND r.dir = true AND l.dst = r.dst
        )
        SELECT dir,
               gsrc,
               dst,
               prev,
               w,
               dead OR (coalesce(t.w > (SELECT f.w/2 FROM found f), false)),
               e.iter + 1 AS iter
        FROM fullTable t, (SELECT iter FROM toExplore limit 1) e
    )
),
ss(dir, gsrc, dst, prev, w, iter) AS (
    SELECT dir, gsrc, dst, prev, w, iter FROM shorts WHERE iter = (SELECT max(iter) FROM shorts)
),
result(f, t, inter, w) AS (
    SELECT l.gsrc, r.gsrc, l.dst, l.w + r.w
    FROM ss l, ss r
    WHERE l.dir = false AND r.dir = true AND l.dst = r.dst
    ORDER BY l.w + r.w
    LIMIT 1
),
sp1(arr, cur) as (
    SELECT ARRAY[inter]::bigint[], inter FROM result
    UNION ALL
    SELECT array_prepend(ss.prev, sp1.arr), ss.prev
    FROM ss, sp1
    WHERE ss.dir = false AND ss.dst = sp1.cur AND ss.prev <> ss.dst
),
sp2(arr, cur) as (
    SELECT (SELECT arr FROM sp1 WHERE cur = (SELECT f FROM result)), (SELECT inter FROM result)
    UNION ALL
    SELECT array_append(sp2.arr, ss.prev), ss.prev
    FROM ss, sp2
    WHERE ss.dir = true AND ss.dst = sp2.cur AND ss.prev <> ss.dst
)
SELECT sp2.arr AS personIdsInPath, result.w AS pathWeight
FROM result, sp2
WHERE sp2.cur = result.t
;
