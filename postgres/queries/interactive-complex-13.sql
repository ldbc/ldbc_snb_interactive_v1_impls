/* Q13. Single shortest path
\set person1Id 17592186044461
\set person2Id 15393162788877
 */
WITH RECURSIVE search_graph(link, depth, path) AS (
        SELECT :person1Id::bigint, 0, ARRAY[:person1Id::bigint]::bigint[]
      UNION ALL
          (WITH sg(link,depth) as (select * from search_graph) -- Note: sg is only the diff produced in the previous iteration
          SELECT distinct person2id, x.depth+1, array_append(path, person2id)
          FROM Person_knows_Person, sg x
          WHERE 1=1
          and x.link = person1id
          and person2id <> ALL (path)
          -- stop if we have reached person2 in the previous iteration
          and not exists(select * from sg y where y.link = :person2Id::bigint)
          -- skip reaching persons reached in the previous iteration
          and not exists(select * from sg y where y.link = person2id)
        )
)
select max(depth) from (
select depth from search_graph where link = :person2Id::bigint
union select -1) tmp;
;
