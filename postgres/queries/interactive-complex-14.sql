/* Q14. Trusted connection paths
\set person1Id 17592186044461
\set person2Id 15393162788877
 */
WITH start_node(v) AS (
    SELECT :person1Id::bigint
)
select * from (
    WITH RECURSIVE
    search_graph(link, depth, path) AS (
            (SELECT v::bigint, 0, ARRAY[]::bigint[][] from start_node)
          UNION ALL
            (WITH sg(link, depth) as (select * from search_graph)
            SELECT distinct person2id, x.depth + 1, path || ARRAY[[x.link, person2id]]
            FROM Person_knows_Person, sg x
            WHERE x.link = person1id and not exists(select * from sg y where y.link = :person2Id::bigint) and not exists(select * from sg y where y.link=person2id)
            )
    ),
    paths(pid, path) AS (
        SELECT row_number() OVER (), path FROM search_graph where link = :person2Id::bigint
    ),
    edges(id, e) AS (
        SELECT pid, array_agg(path[d1][d2])
        FROM paths, generate_subscripts(path, 1) d1, generate_subscripts(path, 2) d2
        GROUP BY pid, d1
    ),
    unique_edges(e) AS (
        SELECT DISTINCT e from edges
    ),
    weights(we, score) as (
        select e, sum(score) from (
            select e, mid1, mid2, max(score) as score from (
                select e, 1 as score, p1.messageid as mid1, p2.messageid as mid2 from unique_edges, message p1, message p2 where (p1.CreatorPersonId=e[1] and p2.CreatorPersonId=e[2] and p2.ParentMessageId=p1.messageid and p1.ParentMessageId is null)
                union all
                select e, 1 as score, p1.messageid as mid1, p2.messageid as mid2 from unique_edges, message p1, message p2 where (p1.CreatorPersonId=e[2] and p2.CreatorPersonId=e[1] and p2.ParentMessageId=p1.messageid and p1.ParentMessageId is null)
                union all
                select e, 0.5 as score, p1.messageid as mid1, p2.messageid as mid2 from unique_edges, message p1, message p2 where (p1.CreatorPersonId=e[1] and p2.CreatorPersonId=e[2] and p2.ParentMessageId=p1.messageid and p1.ParentMessageId is not null)
                union all
                select e, 0.5 as score, p1.messageid as mid1, p2.messageid as mid2  from unique_edges, message p1, message p2 where (p1.CreatorPersonId=e[2] and p2.CreatorPersonId=e[1] and p2.ParentMessageId=p1.messageid and p1.ParentMessageId is not null)
            ) pps group by e, mid1, mid2
        ) tmp
        group by e
    ),
    weightedpaths(path, score) as (
        select path, coalesce(sum(score), 0) from paths, edges left join weights on we=e where pid=id group by id, path
    )
    select path, score from weightedpaths order by score desc)
x  order by score desc;
;
