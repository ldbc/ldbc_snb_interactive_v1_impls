WITH RECURSIVE
    search_graph(link, level, path) AS (
            (SELECT :person1Id::int64, 0, []::int64[][])
          UNION ALL
            (WITH sg(link, level) as (select * from search_graph)
            SELECT DISTINCT k_person2id, x.level + 1, array_append(path, [x.link, k_person2id])
            FROM knows, sg x
            WHERE x.link = k_person1id and not exists(select * from sg y where y.link = :person2Id::int64) and not exists(select * from sg y where y.link=k_person2id)
            )
    ),
    paths(pid, path) AS (
        SELECT row_number() OVER (), path FROM search_graph where link = :person2Id::int64
    ),
    edges AS (
         SELECT pid AS id, path[unnest(generate_series(0, len(path)-1))] as e
         FROM paths
    ),
    unique_edges(e) AS (
        SELECT DISTINCT e from edges
    ),
    weights(we, score) as (
        select e, sum(score) from (
            select e, mid1, mid2, max(score) as score from (
                select e, 1 as score, p1.m_messageid as mid1, p2.m_messageid as mid2 from unique_edges, message p1, message p2 where (p1.m_creatorid=e[0] and p2.m_creatorid=e[1] and p2.m_c_replyof=p1.m_messageid and p1.m_c_replyof is null)
                union all
                select e, 1 as score, p1.m_messageid as mid1, p2.m_messageid as mid2 from unique_edges, message p1, message p2 where (p1.m_creatorid=e[1] and p2.m_creatorid=e[0] and p2.m_c_replyof=p1.m_messageid and p1.m_c_replyof is null)
                union all
                select e, 0.5 as score, p1.m_messageid as mid1, p2.m_messageid as mid2 from unique_edges, message p1, message p2 where (p1.m_creatorid=e[0] and p2.m_creatorid=e[1] and p2.m_c_replyof=p1.m_messageid and p1.m_c_replyof is not null)
                union all
                select e, 0.5 as score, p1.m_messageid as mid1, p2.m_messageid as mid2  from unique_edges, message p1, message p2 where (p1.m_creatorid=e[1] and p2.m_creatorid=e[0] and p2.m_c_replyof=p1.m_messageid and p1.m_c_replyof is not null)
            ) pps group by e, mid1, mid2
        ) tmp
        group by e
    ),
    weightedpaths(path, score) as (
        select path, coalesce(sum(score), 0) from paths, edges left join weights on we=e where pid=id group by id, path
    )
select personIdsInPath, score
from (
    select path, string_agg(e[0], ';') || ';' || :person2Id::int64 as personIdsInPath, score
    from (
        select path, unnest(path) as e, score
        from weightedpaths
    )
    group by path, score
    order by score desc
);
