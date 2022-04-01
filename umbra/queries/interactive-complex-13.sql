WITH RECURSIVE
    search_graph(endNode, level, path, endReached) AS (
            SELECT
                :person1Id::bigint AS endNode,
                0 AS level,
                array[:person1Id::bigint] AS path,
                0 as endReached
        UNION ALL
            SELECT
                k_person2id AS endNode,
                sgx.level + 1 AS level,
                array_append(path, k_person2id) AS path,
                max(CASE WHEN k_person2id = :person2Id::bigint THEN 1 ELSE 0 END) OVER (ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING) AS endReached
            FROM knows
            JOIN search_graph sgx
            ON sgx.endNode = k_person1id
            -- stop if we have reached person2 in the previous iteration
            WHERE sgx.endReached = 0
            -- skip reaching persons reached in the previous iteration
              AND NOT EXISTS (select * from search_graph sgy where sgy.endNode = k_person2id)
            -- an alternative solution would be something like:
            -- NOT EXISTS (SELECT * FROM search_graph sgy WHERE k_person2id = ANY(sgy.path))
            -- but this results in the error "quantified expressions over arrays not implemented yet"
)
select max(depth) AS shortestPathLength from (
    select level as depth
    from search_graph
    where endNode = :person2Id::bigint
    union all
    select -1 as depth
);
