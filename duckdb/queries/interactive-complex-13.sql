WITH RECURSIVE
    search_graph(link, level, path) AS (
            SELECT :person1Id::int64, 0, [:person1Id::int64]
        UNION ALL
            (WITH sg(link, level) as (select * from search_graph) -- Note: sg is only the diff produced in the previous iteration
            SELECT DISTINCT k_person2id, x.level + 1, array_append(path, k_person2id)
            FROM knows, sg x
            WHERE 1=1
            and x.link = k_person1id
            -- stop if we have reached person2 in the previous iteration
            and not exists(select * from sg y where y.link = :person2Id::int64)
            -- skip reaching persons reached in the previous iteration
            and not exists(select * from sg y where y.link = k_person2id)
          )
)
select max(level) AS shortestPathLength from (
select level from search_graph where link = :person2Id
union select -1) tmp;
