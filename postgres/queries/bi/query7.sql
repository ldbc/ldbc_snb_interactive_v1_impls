/* Q7. Most authoritative users on a given topic
\set tag '\'Arnold_Schwarzenegger\''
 */
WITH poster_w_liker AS (
  SELECT DISTINCT
         m1.ps_creatorid posterPersonid
       , l2.l_personid as likerPersonid
    FROM tag t
       , post_tag pt
       -- as an optimization, we use that the set of message1 is the same as message2
       , post m1 LEFT JOIN likes l2 ON (m1.ps_postid = l2.l_postid)
       --, person p2 -- we don't need the person itself as its ID is in the like l2
   WHERE 1=1
      -- join
     AND t.t_tagid = pt.pst_tagid
     AND pt.pst_postid = m1.ps_postid
      -- filter
     AND t.t_name = :tag
)
, popularity_score AS (
  SELECT m3.ps_creatorid as personid, count(*) as popularityScore
    FROM post m3
       , likes l3
   WHERE 1=1
      -- join
     AND m3.ps_postid = l3.l_postid
   GROUP BY personId
)
SELECT pl.posterPersonid as "person1.id"
     , sum(coalesce(ps.popularityScore, 0)) as authorityScore
  FROM poster_w_liker pl LEFT JOIN popularity_score ps ON (pl.likerPersonid = ps.personid)
 GROUP BY pl.posterPersonid
 ORDER BY authorityScore DESC, pl.posterPersonid ASC
 LIMIT 100
;
