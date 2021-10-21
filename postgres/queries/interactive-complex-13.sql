/* Q13. Single shortest path
\set person1Id 8796093022390
\set person2Id 8796093022357
*/
with recursive
search_graph(id, edge, path, level)
as (
select k_person1id, k_person2id, ARRAY[k_person1id], 1
 from knows k
 where k_person1id = :person1Id::bigint
union all
select id, edge, path, level from (
 select sg.id as id, k.k_person2id as edge, path || k.k_person1id as path, sg.level+1 as level
       ,row_number() OVER (PARTITION BY sg.id, k.k_person2id ORDER BY sg.level) as rn
  from knows k, search_graph sg
  where k.k_person1id = sg.edge
    and k.k_person1id <> all(path)
 ) sg_all where rn = 1
)
select level from search_graph
where edge = :person2Id::bigint
limit 1;
