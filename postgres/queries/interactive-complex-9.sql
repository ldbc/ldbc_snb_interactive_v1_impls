/* Q9. Recent messages by friends or friends of friends
\set personId 4398046511268
\set maxDate '\'2010-11-16\''
 */
select p_personid, p_firstname, p_lastname,
       m_messageid, COALESCE(m_ps_imagefile, m_content), m_creationdate
from
  ( select k_person2id
    from knows
    where k_person1id = :personId
    union
    select k2.k_person2id
    from knows k1, knows k2
    where k1.k_person1id = :personId
      and k1.k_person2id = k2.k_person1id
      and k2.k_person2id <> :personId
  ) f, person, message
where
  p_personid = m_creatorid and p_personid = f.k_person2id and
  m_creationdate < :maxDate::date
order by m_creationdate desc, m_messageid asc
limit 20
;
