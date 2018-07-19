select p_personid, p_firstname, p_lastname, m_messageid, COALESCE(m_ps_imagefile,'')||COALESCE(m_content,''), m_creationdate
from person, message, knows
where
    p_personid = m_creatorid and
    m_creationdate <= :Date0 and
    k_person1id = :Person and
    k_person2id = p_personid
order by m_creationdate desc, p_personid asc
limit 20
