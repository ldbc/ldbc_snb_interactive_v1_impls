/* Q13. Popular Tags per month in a country
\set country  '\'Belarus\''
 */
WITH detail AS (
SELECT extract(YEAR FROM m.ps_creationdate) as year
     , extract(MONTH FROM m.ps_creationdate) as month
     , t.t_name as tagName
     , count(DISTINCT m.ps_postid) as popularity
     , row_number() OVER (PARTITION BY extract(YEAR FROM m.ps_creationdate), extract(MONTH FROM m.ps_creationdate) ORDER BY count(DISTINCT m.ps_postid) DESC, t.t_name) as rn
  FROM post m
     , place c -- country
     , post_tag pt
     , tag t
 WHERE 1=1
    -- join
   AND m.ps_locationid = c.pl_placeid
   AND m.ps_postid = pt.pst_postid
   AND pt.pst_tagid = t.t_tagid
    -- filter
   AND c.pl_name = :country
 GROUP BY year, month, t.t_name
)
SELECT year, month, tagName, popularity
  FROM detail
 WHERE rn <= 5
 ORDER BY year DESC, month, popularity DESC, tagName
 LIMIT 100
;
