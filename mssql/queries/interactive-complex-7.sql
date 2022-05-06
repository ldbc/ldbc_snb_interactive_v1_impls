SELECT p_personid
     , p_firstname
     , p_lastname
     , l.l_creationdate
     , m_messageid
     , COALESCE(m_ps_imagefile, m_content)
     , DATEDIFF_BIG(second, m_creationdate, l.l_creationdate)/60 AS minutesLatency
     , (CASE WHEN EXISTS (SElECT 1
                            FROM knows
                           WHERE k_person1id = :personId
                             AND k_person2id = p_personid)
                            THEN 0 ELSE 1 END
       ) AS isnew
  FROM (SELECT TOP(20) l_personid
                     , max(l_creationdate) AS l_creationdate
                  FROM likes
                     , message
                 WHERE m_messageid = l_messageid
                   AND m_creatorid = :personId
              GROUP BY l_personid
              ORDER BY l_creationdate DESC
       ) tmp, message, person, likes AS l
 WHERE p_personid = tmp.l_personid
   AND tmp.l_personid = l.l_personid
   AND tmp.l_creationdate = l.l_creationdate
   AND l.l_messageid = m_messageid
 ORDER BY l_creationdate DESC, p_personid ASC
;
