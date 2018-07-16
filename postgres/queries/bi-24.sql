/* Q24. Messages by Topic and Continent
\set tagClass '\'Album\''
 */
SELECT count(DISTINCT m.ps_postid) AS messageCount
     -- joining with message_tag multiplies message records, hence they are DISTINCT'ed when counting likes
     , count(DISTINCT l.l_messageid||','||l.l_personid) AS likeCount
     , extract(YEAR FROM m.ps_creationdate) AS year
     , extract(MONTH FROM m.ps_creationdate) AS month
     , con.pl_name AS "continent.name"
  FROM tagclass tc
     , tag t
     , message_tag mt
     , post m LEFT JOIN likes l ON (m.ps_postid = l.l_messageid)
     , place cou -- country
     , place con -- continent
 WHERE 1=1
    -- join
   AND tc.tc_tagclassid = t.t_tagclassid
   AND t.t_tagid = mt.mt_tagid
   AND mt.mt_messageid = m.ps_postid
   AND m.ps_locationid = cou.pl_placeid
   AND cou.pl_containerplaceid = con.pl_placeid
    -- filter
   AND tc.tc_name = :tagClass
 GROUP BY year, month, con.pl_name
 ORDER BY year, month, con.pl_name DESC
 LIMIT 100
;
