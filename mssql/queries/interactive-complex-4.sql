SELECT TOP(10) t_name
             , count(*) AS postCount
          FROM tag
             , message
             , message_tag recent
             , knows
         WHERE m_messageid = mt_messageid
           AND mt_tagid = t_tagid
           AND m_creatorid = k_person2id
           AND m_c_replyof IS NULL
           AND k_person1id = :personId
           AND m_creationdate >= :startDate
           AND m_creationdate < DATEADD(day, :durationDays, :startDate)
           AND NOT EXISTS (
                SELECT *
                  FROM (SELECT DISTINCT mt_tagid
                          FROM message
                             , message_tag
                             , knows
                         WHERE k_person1id = :personId
                          AND k_person2id = m_creatorid
                          AND m_c_replyof IS NULL
                          AND mt_messageid = m_messageid
                          AND m_creationdate < :startDate
                            ) tags
                WHERE tags.mt_tagid = recent.mt_tagid)
      GROUP BY t_name
      ORDER BY postCount DESC, t_name ASC
;
