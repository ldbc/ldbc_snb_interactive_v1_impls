SELECT TOP(20) p_personid
             , p_firstname
             , p_lastname
             , m_messageid
             , COALESCE(m_ps_imagefile, m_content)
             , m_creationdate
          FROM person, message, knows
         WHERE p_personid = m_creatorid 
           AND m_creationdate <= :maxDate
           AND k_person1id = :personId 
           AND k_person2id = p_personid
      ORDER BY m_creationdate DESC, m_messageid ASC
;
