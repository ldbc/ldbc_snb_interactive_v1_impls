/* Q3. Friends and friends of friends that have been to given countries
\set personId 6597069766734
\set countryXName '\'Angola\''
\set countryYName '\'Colombia\''
\set startDate '\'2010-06-01\''::date
\set durationDays 28
 */
-- SELECT TOP(20) p_personid, p_firstname, p_lastname, ct1, ct2, total AS totalcount
--   FROM ( 
--     SELECT k_person2id
--       FROM knows
--      WHERE k_person1id = :personId
--      UNION
--     SELECT k2.k_person2id
--       FROM knows k1, knows k2
--      WHERE k1.k_person1id = :personId
--        AND k1.k_person2id = k2.k_person1id 
--        AND k2.k_person2id <> :personId
--   ) f, person, place p1, place p2, (
--     SELECT chn.m_c_creatorid, ct1, ct2, ct1 + ct2 AS total
--       FROM (
--         SELECT m_creatorid AS m_c_creatorid, count(*) AS ct1 
--           FROM message, place
--          WHERE m_locationid = pl_placeid 
--            AND pl_name = :countryXName 
--            AND m_creationdate >= :startDate 
--            AND m_creationdate < DATEADD(day, :durationDays, :startDate)
--       GROUP BY m_c_creatorid
--       ) chn, (
--             SELECT m_creatorid AS m_c_creatorid, count(*) AS ct2 
--               FROM message, place
--               WHERE m_locationid = pl_placeid 
--                 AND pl_name = :countryYName 
--                 AND m_creationdate >= :startDate 
--                 AND m_creationdate < DATEADD(day, :durationDays, :startDate)
--           GROUP BY m_creatorid 
--         ) ind
--         WHERE CHN.m_c_creatorid = IND.m_c_creatorid
--     ) cpc
--     WHERE f.k_person2id = p_personid 
--       AND p_placeid = p1.pl_placeid 
--       AND p1.pl_containerplaceid = p2.pl_placeid
--       AND p2.pl_name <> :countryXName 
--       AND p2.pl_name <> :countryYName 
--       AND f.k_person2id = cpc.m_c_creatorid
--  ORDER BY totalcount DESC, p_personid ASC
-- ;


select top(20) p_personid, p_firstname, p_lastname, ct1, ct2, total AS totalcount
from
 ( select k_person2id
   from knows
   where
   k_person1id = :personId
   union
   select k2.k_person2id
   from knows k1, knows k2
   where
   k1.k_person1id = :personId and k1.k_person2id = k2.k_person1id and k2.k_person2id <> :personId
 ) f, person, place p1, place p2,
 (
  select chn.m_c_creatorid, ct1, ct2, ct1 + ct2 as total
  from
   (
      select m_creatorid as m_c_creatorid, count(*) as ct1 from message, place
      where
        m_locationid = pl_placeid and pl_name = :countryXName and
        m_creationdate >= :startDate and  m_creationdate < DATEADD(day, :durationDays, :startDate)
      group by m_c_creatorid
   ) chn,
   (
      select m_creatorid as m_c_creatorid, count(*) as ct2 from message, place
      where
        m_locationid = pl_placeid and pl_name = :countryYName and
        m_creationdate >= :startDate and  m_creationdate < DATEADD(day, :durationDays, :startDate)
      group by m_creatorid --m_c_creatorid
   ) ind
  where CHN.m_c_creatorid = IND.m_c_creatorid
 ) cpc
where
f.k_person2id = p_personid and p_placeid = p1.pl_placeid and
p1.pl_containerplaceid = p2.pl_placeid and p2.pl_name <> :countryXName and p2.pl_name <> :countryYName and
f.k_person2id = cpc.m_c_creatorid
order by totalcount desc, p_personid asc
;
