SELECT TOP(20) f_title
             , count(m_messageid) AS postCount
          FROM (SELECT f_title
                     , f_forumid
                     , f.k_person2id
                  FROM forum
                     , forum_person
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
                 WHERE f_forumid = fp_forumid
                   AND fp_personid = f.k_person2id
                   AND fp_joindate >= :minDate
                     ) tmp
             LEFT JOIN message ON tmp.f_forumid = m_ps_forumid
                   AND m_creatorid = tmp.k_person2id
              GROUP BY f_forumid, f_title
              ORDER BY postCount DESC, f_forumid ASC
;
