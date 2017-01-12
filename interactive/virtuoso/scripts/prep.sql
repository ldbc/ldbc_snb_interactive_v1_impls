xml_set_ns_decl ('snvoc', 'http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/', 2);
xml_set_ns_decl ('sn', 'http://www.ldbc.eu/ldbc_socialnet/1.0/data/', 2);
xml_set_ns_decl ('dbpedia-owl', 'http://dbpedia.org/ontology/', 2);


create procedure path_str_sparql (in path any)
{
  declare str any;
  declare inx int;
  str := '';
  foreach (any  st  in path) do
    str := str || sprintf (' %ld->%ld (%g) ', cast (substring(st[0], 48, 20) as int), coalesce(cast (substring(st[1], 48, 20) as int), 0), coalesce (st[2], 0));
  return str;
}

create function c_weight_sparql1 (in p1 varchar, in p2 varchar) returns decimal
{
  -- TODO: vectored
  if (p1 is null or p2 is null)
     return 0;
  return
  	 ( sparql select count(*) from <sib> where {?post1 snvoc:hasCreator  `iri(?:p1)`. ?post1 snvoc:replyOf ?post2. ?post2 snvoc:hasCreator  `iri(?:p2)`. ?post2 a snvoc:Post} ) +
  	 ( sparql select count(*) from <sib> where {?post1 snvoc:hasCreator `iri(?:p2)`. ?post1 snvoc:replyOf ?post2. ?post2 snvoc:hasCreator  `iri(?:p1)`. ?post2 a snvoc:Post} ) +
  	 ( sparql select 0.5 * count(*) from <sib> where {?post1 snvoc:hasCreator  `iri(?:p1)`. ?post1 snvoc:replyOf ?post2. ?post2 snvoc:hasCreator `iri(?:p2)`. ?post2 a snvoc:Comment} ) +
  	 ( sparql select 0.5 * count(*) from <sib> where {?post1 snvoc:hasCreator `iri(?:p2)`. ?post1 snvoc:replyOf ?post2. ?post2 snvoc:hasCreator  `iri(?:p1)`. ?post2 a snvoc:Comment} );
}

create procedure c_weight_sparql (in p1 varchar, in p2 varchar)
{
  vectored;
  if (p1 is null or p2 is null)
     return 0;
  return 
    (SELECT COUNT (*)
     FROM RDF_QUAD AS r0
       INNER JOIN DB.DBA.RDF_QUAD AS r1
       ON ( r0.S  = r1.S )
       INNER JOIN DB.DBA.RDF_QUAD AS r2
       ON ( r1.O  = r2.S )
       INNER JOIN DB.DBA.RDF_QUAD AS r3
       ON ( r1.O  = r3.S 
       	  AND  r2.S  = r3.S )
     WHERE  r0.P = __i2idn ( 'http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasCreator')
       AND  r1.P = __i2idn ( 'http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/replyOf')
       AND  r2.P = __i2idn ( 'http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasCreator')
       AND  r3.P = __i2idn ( 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
       AND  r3.O = __i2idn ( 'http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/Post')
       AND  r0.O = __i2idn (p1) AND r2.O = __i2idn (p2)) +
    (SELECT COUNT (*)
     FROM RDF_QUAD AS r0
       INNER JOIN DB.DBA.RDF_QUAD AS r1
       ON ( r0.S  = r1.S )
       INNER JOIN DB.DBA.RDF_QUAD AS r2
       ON ( r1.O  = r2.S )
       INNER JOIN DB.DBA.RDF_QUAD AS r3
       ON ( r1.O  = r3.S 
       	  AND  r2.S  = r3.S )
     WHERE  r0.P = __i2idn ( 'http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasCreator')
       AND  r1.P = __i2idn ( 'http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/replyOf')
       AND  r2.P = __i2idn ( 'http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasCreator')
       AND  r3.P = __i2idn ( 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
       AND  r3.O = __i2idn ( 'http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/Post')
       AND  r0.O = __i2idn (p2) AND r2.O = __i2idn (p1)) +
    (SELECT 0.5 * COUNT (*)
     FROM RDF_QUAD AS r0
       INNER JOIN DB.DBA.RDF_QUAD AS r1
       ON ( r0.S  = r1.S )
       INNER JOIN DB.DBA.RDF_QUAD AS r2
       ON ( r1.O  = r2.S )
       INNER JOIN DB.DBA.RDF_QUAD AS r3
       ON ( r1.O  = r3.S 
       	  AND  r2.S  = r3.S )
     WHERE  r0.P = __i2idn ( 'http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasCreator')
       AND  r1.P = __i2idn ( 'http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/replyOf')
       AND  r2.P = __i2idn ( 'http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasCreator')
       AND  r3.P = __i2idn ( 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
       AND  r3.O = __i2idn ( 'http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/Comment')
       AND  r0.O = __i2idn (p1) AND r2.O = __i2idn (p2)) +
    (SELECT 0.5 * COUNT (*)
     FROM RDF_QUAD AS r0
       INNER JOIN DB.DBA.RDF_QUAD AS r1
       ON ( r0.S  = r1.S )
       INNER JOIN DB.DBA.RDF_QUAD AS r2
       ON ( r1.O  = r2.S )
       INNER JOIN DB.DBA.RDF_QUAD AS r3
       ON ( r1.O  = r3.S 
       	  AND  r2.S  = r3.S )
     WHERE  r0.P = __i2idn ( 'http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasCreator')
       AND  r1.P = __i2idn ( 'http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/replyOf')
       AND  r2.P = __i2idn ( 'http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasCreator')
       AND  r3.P = __i2idn ( 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
       AND  r3.O = __i2idn ( 'http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/Comment')
       AND  r0.O = __i2idn (p2) AND r2.O = __i2idn (p1));
}

--insert into rdf_quad
--select a.g, a.s, a.p, b.o
--from rdf_quad a, rdf_quad b
--where a.g = iri_to_id('sib') and a.p = iri_to_id('http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/knows') and
--      a.o = b.s and
--      b.g = iri_to_id('sib') and b.p = iri_to_id('http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasPerson');

--create procedure fill_knows () {
--       vectored;
--       for (
--       	   select a.s as s, b.o as o, c.o as cd
--	   from rdf_quad a, rdf_quad b, rdf_quad c
--	   where a.g = iri_to_id('sib') and a.p = iri_to_id('http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/knows') and
--	   	 a.o = b.s and
--      		 b.g = iri_to_id('sib') and
--		 b.p = iri_to_id('http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasPerson') and
--		 a.o = c.s and
--      		 c.g = iri_to_id('sib') and
--		 c.p = iri_to_id('http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/creationDate')
--       ) do {
--         --sparql insert in graph <sib> { ?:s snvoc:knows ?:o };
--       	 --sparql insert in graph <sib> { ?:o snvoc:knows ?:s };
--	 sparql insert in graph <sib> { ?:o snvoc:knows _:knows_tmp. _:knows_tmp snvoc:hasPerson ?:s. _:knows_tmp snvoc:creationDate ?:cd. };
--       }
--}

--fill_knows();

--__dbf_set( 'enable_qp', 1);
--log_enable(2);
--sparql insert in graph <sib> {?s snvoc:knows ?o1. ?o1 snvoc:knows ?s. ?o1 snvoc:knows [snvoc:hasPerson ?s; snvoc:creationDate ?cd].} where { graph <sib> { ?s snvoc:knows ?o. ?o snvoc:hasPerson ?o1. ?o snvoc:creationDate ?cd. }};
--__dbf_set( 'enable_qp', 24);
