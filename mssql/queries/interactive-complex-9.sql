SELECT TOP(20) p_personid
             , p_firstname
             , p_lastname
             , m_messageid
             , COALESCE(m_ps_imagefile, m_content)
             , m_creationdate
          FROM (SELECT k_person2id
                  FROM knows
                 WHERE k_person1id = :personId
                 UNION
                SELECT k2.k_person2id
                  FROM knows k1
                     , knows k2
                 WHERE k1.k_person1id = :personId
                   AND k1.k_person2id = k2.k_person1id
                   AND k2.k_person2id <> :personId
               ) f, person, message
         WHERE p_personid = m_creatorid
           AND p_personid = f.k_person2id
           AND m_creationdate < :maxDate
      ORDER BY m_creationdate DESC, m_messageid ASc
;
