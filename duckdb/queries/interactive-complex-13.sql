/* Q13. Single shortest path
\set person1Id 8796093022390
\set person2Id 8796093022357
 */
WITH RECURSIVE search_graph(link, level, path) AS (
        SELECT cast(:person1Id as int64), 0, cast(:person1Id as string) -- ARRAY[:person1Id::bigint]::bigint[]
      UNION ALL
          (WITH sg(link, level) as (select * from search_graph) -- Note: sg is only the diff produced in the previous iteration
          SELECT distinct k_person2id, x.level+1, case when path = '' then cast(k_person2id as string) else concat(path, ';', k_person2id) end
          FROM knows, sg x
          WHERE 1=1
          and x.link = k_person1id
          and not regexp_matches(path, concat('[^0-9]', k_person2id, '[^0-9]')) -- 'k_person2id <> ALL (path)' expressed with a regex
          -- stop if we have reached person2 in the previous iteration
          and not exists(select * from sg y where y.link = :person2Id)
          -- skip reaching persons reached in the previous iteration
          and not exists(select * from sg y where y.link = k_person2id)
        )
)
select max(level) AS shortestPathLength from (
select level from search_graph where link = :person2Id
union select -1) tmp;
