create procedure types ( in graph VARCHAR )
{
  declare tt VARCHAR;
  result_names (tt);

  whenever not found goto done;
  declare cr cursor for
  	  select id_to_iri(O) from (
	  	 select distinct O from RDF_QUAD
		 where G = iri_to_id(graph)
		   and P = iri_to_id('http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
          ) as tmp
  ;

  open cr;
  while (1)
    {
      fetch cr into tt;
      result (tt);
    }

done:
  close cr;
}

create procedure instances ( in graph VARCHAR, in t LONG VARCHAR)
{
  declare tt LONG VARCHAR;
  result_names (tt);

  whenever not found goto done;
  declare cr cursor for
  	  select id_to_iri(S) from (
	  	 select distinct S from RDF_QUAD
		 where G = iri_to_id(graph)
		   and P = iri_to_id('http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
		   and O = iri_to_id(t)
          ) as tmp
  ;

  open cr;
  while (1)
    {
      fetch cr into tt;
      result (tt);
    }

done:
  close cr;
}

create procedure instances_count ( in graph VARCHAR, in t LONG VARCHAR)
{
  declare cnt bigint;
  select count(distinct S) into cnt
  from RDF_QUAD
  where G = iri_to_id(graph)
    and P = iri_to_id('http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
    and O = iri_to_id(t)
  ;

  return cnt;
}

create procedure predicates (  in graph VARCHAR, in t LONG VARCHAR)
{
  declare tt LONG VARCHAR;
  result_names (tt);

  whenever not found goto done;
  declare cr cursor for
  	  select id_to_iri(P) from (
	  	 select distinct t2.P from RDF_QUAD t1, RDF_QUAD t2
		 where t1.S = t2.S
		   and t1.G = iri_to_id(graph)
 		   and t2.G = iri_to_id(graph)
		   and t1.P = iri_to_id('http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
		   and t1.O = iri_to_id(t)
		   and t2.P <> iri_to_id('http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
          ) as tmp
  ;

  open cr;
  while (1)
    {
      fetch cr into tt;
      result (tt);
    }

done:
  close cr;
}

create procedure predicates_count (  in graph VARCHAR, in t LONG VARCHAR)
{
  declare cnt bigint;
  
  select count(distinct t2.P) into cnt
  from RDF_QUAD t1, RDF_QUAD t2
  where t1.S = t2.S
    and t1.G = iri_to_id(graph)
    and t2.G = iri_to_id(graph)
    and t1.P = iri_to_id('http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
    and t1.O = iri_to_id(t)
    and t2.P <> iri_to_id('http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
  ;

  return cnt;
}

create procedure occurrences (  in graph VARCHAR, in t LONG VARCHAR, in pred LONG VARCHAR)
{
  declare cnt bigint;

  select count(distinct t1.S) into cnt
  from RDF_QUAD t1, RDF_QUAD t2
  where t1.S = t2.S
    and t1.G = iri_to_id(graph)
    and t2.G = iri_to_id(graph)
    and t1.P = iri_to_id('http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
    and t1.O = iri_to_id(t)
    and t2.P = iri_to_id(pred)
  ;

  return cnt;
}

create procedure coverage (  in graph VARCHAR, in t LONG VARCHAR)
{
  declare a bigint;
  declare b bigint;
  declare c bigint;

  select sum(cnt) into a
  from (
       select t2.P as pred, count(distinct t1.S) as cnt
       from RDF_QUAD t1, RDF_QUAD t2
       where t1.S = t2.S
       	 and t1.G = iri_to_id(graph)
	 and t2.G = iri_to_id(graph)
	 and t1.P = iri_to_id('http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
	 and t1.O = iri_to_id(t)
	 and t2.P <> iri_to_id('http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
       group by pred
  ) tmp
  ;

  select count(distinct t2.P) into b
  from RDF_QUAD t1, RDF_QUAD t2
  where t1.S = t2.S
    and t1.G = iri_to_id(graph)
    and t2.G = iri_to_id(graph)
    and t1.P = iri_to_id('http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
    and t1.O = iri_to_id(t)
    and t2.P <> iri_to_id('http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
  ;

  select count(distinct S) into c
  from RDF_QUAD
  where G = iri_to_id(graph)
    and P = iri_to_id('http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
    and O = iri_to_id(t)
  ;

  return cast (a as real) / (b * c);
}

--select types('tmptmp');
--
--select instances('tmptmp',
--		 'Person');
--		 
--select predicates('tmptmp',
--		  'Person');
--
--select occurrences('tmptmp',
--                   'Person',
--		   'major');
--
--select coverage('tmptmp',
--                'Person');
--


--select types('http://ldbcouncil.org/');
--select instances('http://ldbcouncil.org/',
--                 'http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/Person');

--select instances_count('http://ldbcouncil.org/',
--                       'http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/Person');

--select predicates('http://ldbcouncil.org/',
--                  'http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/Person');

--select predicates_count('http://ldbcouncil.org/',
--                  'http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/Person');

--select occurrences('http://ldbcouncil.org/',
--                   'http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/Person',
--		     'http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/knows');

--select coverage('http://ldbcouncil.org/',
--                'http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/Person');


create procedure total_preds_and_insts (  in graph VARCHAR) {
  declare a bigint;

  select sum(pred_cnt + inst_cnt) into a
  from (
    select id_to_iri(O) as type,
           predicates_count(graph, id_to_iri(O)) as pred_cnt,
           instances_count(graph, id_to_iri(O)) as inst_cnt
    from (
      select distinct O from RDF_QUAD
      where G = iri_to_id(graph)
        and P = iri_to_id('http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
    ) as tmp
  ) tmp1
  ;

  return a;
}

create procedure coherence (  in graph VARCHAR) {
  declare a real;

  select sum(coverage * (pred_cnt + inst_cnt)) into a
  from (
    select id_to_iri(O) as type,
           coverage(graph, id_to_iri(O)) as coverage,
           predicates_count(graph, id_to_iri(O)) as pred_cnt,
           instances_count(graph, id_to_iri(O)) as inst_cnt
    from (
      select distinct O from RDF_QUAD
      where G = iri_to_id(graph)
        and P = iri_to_id('http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
    ) as tmp
  ) tmp1
  ;

  return a / total_preds_and_insts(graph);
}

select coherence('http://ldbcouncil.org/');

select type, coverage,
       (cast ((pred_cnt + inst_cnt) as real) / total_preds_and_insts('http://ldbcouncil.org/')) as weight,
       coverage * (cast ((pred_cnt + inst_cnt) as real) / total_preds_and_insts('http://ldbcouncil.org/')) as f
from (
  select id_to_iri(O) as type,
         coverage('http://ldbcouncil.org/', id_to_iri(O)) as coverage,
         predicates_count('http://ldbcouncil.org/', id_to_iri(O)) as pred_cnt,
         instances_count('http://ldbcouncil.org/', id_to_iri(O)) as inst_cnt
  from (
    select distinct O from RDF_QUAD
    where G = iri_to_id('http://ldbcouncil.org/')
      and P = iri_to_id('http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
  ) as tmp
) tmp1
order by f desc
;
