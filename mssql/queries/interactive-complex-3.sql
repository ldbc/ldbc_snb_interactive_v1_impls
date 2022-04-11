/* Q3. Friends and friends of friends that have been to given countries
\set personId 6597069766734
\set countryXName '\'Angola\''
\set countryYName '\'Colombia\''
\set startDate '\'2010-06-01\''::date
\set durationDays 28
 */
SELECT TOP(20) p_personid, p_firstname, p_lastname, ct1, ct2, total AS totalcount
  FROM ( 
    SELECT k_person2id
      FROM knows
     WHERE k_person1id = :personId
     UNION
    SELECT k2.k_person2id
      FROM knows k1, knows k2
     WHERE k1.k_person1id = :personId
       AND k1.k_person2id = k2.k_person1id 
       AND k2.k_person2id <> :personId
  ) f, person, place p1, place p2, (
    SELECT chn.m_c_creatorid, ct1, ct2, ct1 + ct2 AS total
      FROM (
        SELECT m_creatorid AS m_c_creatorid, count(*) AS ct1 
          FROM message, place
         WHERE m_locationid = pl_placeid 
           AND pl_name = :countryXName 
           AND m_creationdate >= :startDate 
           AND m_creationdate < DATEADD(day, :durationDays, :startDate)
      GROUP BY m_c_creatorid
      ) chn, (
            SELECT m_creatorid AS m_c_creatorid, count(*) AS ct2 
              FROM message, place
              WHERE m_locationid = pl_placeid 
                AND pl_name = :countryYName 
                AND m_creationdate >= :startDate 
                AND m_creationdate < DATEADD(day, :durationDays, :startDate)
          GROUP BY m_creatorid 
        ) ind
        WHERE CHN.m_c_creatorid = IND.m_c_creatorid
    ) cpc
    WHERE f.k_person2id = p_personid 
      AND p_placeid = p1.pl_placeid 
      AND p1.pl_containerplaceid = p2.pl_placeid
      AND p2.pl_name <> :countryXName 
      AND p2.pl_name <> :countryYName 
      AND f.k_person2id = cpc.m_c_creatorid
 ORDER BY totalcount DESC, p_personid ASC
;
