/* Q3. Tag evolution
\set year 2010
\set month 11
 */
WITH detail AS (
SELECT t.t_name
     , count(DISTINCT CASE WHEN extract(MONTH FROM m.m_creationdate)  = :month THEN m.m_messageid ELSE NULL END) AS countMonth1
     , count(DISTINCT CASE WHEN extract(MONTH FROM m.m_creationdate) != :month THEN m.m_messageid ELSE NULL END) AS countMonth2
  FROM message m
     , message_tag mt
     , tag t
 WHERE 1=1
    -- join
   AND m.m_messageid = mt.mt_messageid
   AND mt.mt_tagid = t.t_tagid
    -- filter
   AND m.m_creationdate >= make_date(:year, :month, 1)
   AND m.m_creationdate <  make_date(:year, :month, 1) + make_interval(months => 2)
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
