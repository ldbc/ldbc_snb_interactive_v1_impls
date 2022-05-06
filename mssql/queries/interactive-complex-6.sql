SELECT TOP(10) t_name
             , count(*) AS postcount
          FROM tag
             , message_tag
             , message
             , (SELECT k_person2id
                  FROM knows
                 WHERE k_person1id = :personId
                 UNION
                SELECT k2.k_person2id
                  FROM knows k1
                     , knows k2
                 WHERE k1.k_person1id = :personId
                   AND k1.k_person2id = k2.k_person1id
                   AND k2.k_person2id <> :personId
               ) f
         WHERE m_creatorid = f.k_person2id
           AND m_c_replyof IS NULL
           AND m_messageid = mt_messageid
           AND mt_tagid = t_tagid
           AND t_name <> :tagName
           AND EXISTS (SELECT * 
                         FROM tag
                            , message_tag
                        WHERE mt_messageid = m_messageid
                          AND mt_tagid = t_tagid
                          AND t_name = :tagName
                      )
      GROUP BY t_name
      ORDER BY postCount DESC, t_name ASC
;
