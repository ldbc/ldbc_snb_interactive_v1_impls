select t_name, count(*)
from tag, message, message_tag recent, knows
where
    m_messageid = mt_messageid and
    mt_tagid = t_tagid and
    m_ps_creatorid = k_person2id and
    k_person1id = :Person and
    m_creationdate>= :Date0 and  m_creationdate < (:Date0 + INTERVAL '1 days' * :Duration) and
    not exists (
        select * from
  (select distinct mt_tagid from message, message_tag, knows
        where
	k_person1id = :Person and
        k_person2id = m_ps_creatorid and
        mt_messageid = m_messageid and
        m_creationdate < :Date0) tags
  where  tags.mt_tagid = recent.mt_tagid)
group by t_name
order by 2 desc, t_name
limit 10
