/* Q2. Tag evolution
\set date '\'2012-06-01T00:00:00.000+00:00\''::timestamp
\set tagClass '\'MusicalArtist\''
 */
WITH detail AS (
SELECT t.t_name
     , count(DISTINCT CASE WHEN m.m_creationdate <  :date + INTERVAL '100 days' THEN m.m_messageid ELSE NULL END) AS countMonth1
     , count(DISTINCT CASE WHEN m.m_creationdate >= :date + INTERVAL '100 days' THEN m.m_messageid ELSE NULL END) AS countMonth2
  FROM message m
     , message_tag mt
     , tag t
     , tagClass tc
 WHERE 1=1
    -- join
   AND tc.tc_tagclassid = t.t_tagclassid
   AND m.m_messageid = mt.mt_messageid
   AND mt.mt_tagid = t.t_tagid
    -- filter
   AND tc.tc_name = :tagClass
   AND :date <= m.m_creationdate
   AND m.m_creationdate <= :date + INTERVAL '200 days'
 GROUP BY t.t_name
)
SELECT t_name as "tag.name"
     , countMonth1
     , countMonth2
     , abs(countMonth1-countMonth2) AS diff
  FROM detail d
 ORDER BY diff desc, t_name
 LIMIT 100
;
