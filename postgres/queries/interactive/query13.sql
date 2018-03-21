WITH RECURSIVE search_graph(link, depth) AS (
		SELECT :Person1 , 0
      UNION ALL
      	(WITH sg(link,depth) as (select * from search_graph)
      	SELECT distinct k_person2id, x.depth+1
      	FROM knows, sg x
      	WHERE x.link = k_person1id and not exists(select * from sg y where y.link = :Person2) and not exists( select * from sg y where y.link=k_person2id))
)
select max(depth) from (
select depth from search_graph where link = :Person2
union select -1) tmp;
