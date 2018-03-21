WITH start_node(v) AS (
	SELECT :Person1
)
select * from (
	WITH RECURSIVE
	search_graph(link, depth, path) AS (
	        (SELECT v::bigint, 0, ARRAY[]::bigint[][] from start_node)
	      UNION ALL
	        (WITH sg(link,depth) as (select * from search_graph)
	        SELECT distinct k_person2id, x.depth + 1,path || ARRAY[[x.link, k_person2id]]
	        FROM knows, sg x
	        WHERE x.link = k_person1id and not exists(select * from sg y where y.link = :Person2) and not exists( select * from sg y where y.link=k_person2id)
	        )
	),
	paths(pid,path) AS (
		SELECT row_number() OVER (), path FROM search_graph where link = :Person2 
	),
	edges(id,e) AS (
		SELECT pid, array_agg(path[d1][d2])
		FROM  paths, generate_subscripts(path,1) d1,generate_subscripts(path,2) d2
		GROUP  BY pid,d1
	),
	unique_edges(e) AS (
		SELECT DISTINCT e from edges
	),
	weights(we, score) as (
		select e,sum(score) from (
			select e, pid1, pid2, max(score) as score from (
				select e, 1 as score, p1.ps_postid as pid1, p2.ps_postid as pid2 from edges, post p1, post p2 where (p1.ps_creatorid=e[1] and p2.ps_creatorid=e[2] and p2.ps_replyof=p1.ps_postid and p1.ps_replyof is null)
				union all
				select e, 1 as score, p1.ps_postid as pid1, p2.ps_postid as pid2 from edges, post p1, post p2 where (p1.ps_creatorid=e[2] and p2.ps_creatorid=e[1] and p2.ps_replyof=p1.ps_postid and p1.ps_replyof is null)
				union all
				select e, 0.5 as score, p1.ps_postid as pid1, p2.ps_postid as pid2 from edges, post p1, post p2 where (p1.ps_creatorid=e[1] and p2.ps_creatorid=e[2] and p2.ps_replyof=p1.ps_postid and p1.ps_replyof is not null)
				union all
				select e, 0.5 as score, p1.ps_postid as pid1, p2.ps_postid as pid2  from edges, post p1, post p2 where (p1.ps_creatorid=e[2] and p2.ps_creatorid=e[1] and p2.ps_replyof=p1.ps_postid and p1.ps_replyof is not null)
			) pps group by e,pid1,pid2
		) tmp
		group by e
	),
	weightedpaths(path,score) as (
		select path, coalesce(sum(score),0) from paths, edges left outer join weights on we=e where pid=id group by id,path
	)
	select path,score from weightedpaths order by score desc)
x  order by score desc;
