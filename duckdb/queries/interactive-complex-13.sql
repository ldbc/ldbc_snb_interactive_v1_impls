/* Q13. Single shortest path
\set person1Id 8796093022390
\set person2Id 8796093022357
 */
WITH RECURSIVE search_graph(link, level, path) AS (
        SELECT (59 as int64), 0, [59::int64] -- ::int64[]
      UNION ALL
          (WITH sg(link, level) as (select * from search_graph) -- Note: sg is only the diff produced in the previous iteration
          SELECT distinct k_person2id, x.level+1, path || ARRAY[k_person2id] end
          FROM knows, sg x
          WHERE 1=1
          and x.link = k_person1id
          and not exists(select * from sg y where y.link = 133::int64)
                 --not regexp_matches(path, concat('[^0-9]', k_person2id, '[^0-9]')) -- 'k_person2id <> ALL (path)' expressed with a regex
          -- stop if we have reached person2 in the previous iteration
          and not exists(select * from sg y where y.link = 133)
          -- skip reaching persons reached in the previous iteration
          and not exists(select * from sg y where y.link = k_person2id)
        )
)
select max(level) AS shortestPathLength from (
select level from search_graph where link = 133
union select -1) tmp;
