WITH RECURSIVE
    search_graph(link, level, path) AS (
            SELECT :person1Id::bigint, 0, array[:person1Id::bigint]
        UNION ALL
            (WITH sg(link, level) as (select link, level, path from search_graph) -- Note: sg is only the diff produced in the previous iteration
            SELECT DISTINCT k_person2id, x.level + 1, array_append(path, k_person2id)
            FROM knows, sg x
            WHERE 1=1
            and x.link = k_person1id
            -- stop if we have reached person2 in the previous iteration
            and not exists(select * from sg y where y.link = :person2Id::bigint)
            -- skip reaching persons reached in the previous iteration
            and not exists(select * from sg y where y.link = k_person2id)
          )
)
select max(depth) AS shortestPathLength from (
    select level as depth
    from search_graph
    where link = :person2Id::bigint
    union select -1) tmp;
