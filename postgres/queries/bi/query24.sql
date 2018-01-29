/* Q24. Messages by Topic and Continent
\set tagClass '\'Album\''
 */
SELECT count(DISTINCT m.ps_postid) AS messageCount
     -- joining with post_tag multiplies message records, hence they are DISTINCT'ed when counting likes
     , count(DISTINCT l.l_postid||','||l.l_personid) AS likeCount
     , extract(YEAR FROM m.ps_creationdate) AS year
     , extract(MONTH FROM m.ps_creationdate) AS month
     , con.pl_name AS "continent.name"
  FROM tagclass tc
     , tag_tagclass ttc
     , post_tag mt
     , post m LEFT JOIN likes l ON (m.ps_postid = l.l_postid)
     , place cou -- country
     , place con -- continent
 WHERE 1=1
    -- join
   AND tc.tc_tagclassid = ttc.ttc_tagclassid
   AND ttc.ttc_tagid = mt.pst_tagid
   AND mt.pst_postid = m.ps_postid
   AND m.ps_locationid = cou.pl_placeid
   AND cou.pl_containerplaceid = con.pl_placeid
    -- filter
   AND tc.tc_name = :tagClass
 GROUP BY year, month, con.pl_name
 ORDER BY year, month, con.pl_name DESC
 LIMIT 100
;
