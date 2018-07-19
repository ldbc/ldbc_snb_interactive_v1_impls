/* Q3. Tag evolution
\set year 2010
\set month 11
 */
SELECT d.t_name as tagName
     , d.countMonth1
     , d.countMonth2
     , CASE
         WHEN d.countMonth1-d.countMonth2 > 0 THEN d.countMonth1-d.countMonth2
         ELSE d.countMonth2-d.countMonth1
       END AS diff
  FROM (
    SELECT tab1.t_name
        , sum(tab1.belongsToMonth1) AS countMonth1
        , sum(tab1.belongsToMonth2) AS countMonth2
      FROM (
    SELECT t.t_name
        , CASE WHEN extract(MONTH FROM m.m_creationdate) = param.month1 THEN 1 ELSE 0 END AS belongsToMonth1
        , CASE WHEN extract(MONTH FROM m.m_creationdate) = param.month2 THEN 1 ELSE 0 END AS belongsToMonth2
    FROM message m
        , message_tag mt
        , tag t
        , (
            SELECT param_inner.year AS year1
                , param_inner.month AS month1
                , CASE WHEN param_inner.month = 12 THEN param_inner.year + 1 ELSE param_inner.year    END AS year2
                , CASE WHEN param_inner.month = 12 THEN                    1 ELSE param_inner.month+1 END AS month2
            FROM (
                SELECT 2010 AS year, 11 AS month -- FIXME:param :year, :month
                ) param_inner
        ) param
    WHERE 1=1
        -- join
    AND m.m_messageid = mt.mt_messageid
    AND mt.mt_tagid = t.t_tagid
        -- filter
    AND ( 0=1
        OR extract(YEAR FROM m.m_creationdate) = param.year1 AND  extract(MONTH FROM m.m_creationdate) = param.month1
        OR extract(YEAR FROM m.m_creationdate) = param.year2 AND  extract(MONTH FROM m.m_creationdate) = param.month2
        )
      ) tab1
    GROUP BY tab1.t_name
  ) d -- detail
 --ORDER BY diff desc, t_name
 --LIMIT 100
;
