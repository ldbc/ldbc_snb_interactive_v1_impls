select t_name, count(*)
from tag, post, message_tag recent, knows
where
    ps_postid = mt_messageid and
    mt_tagid = t_tagid and
    m_ps_creatorid = k_person2id and
    k_person1id = :Person and
    ps_creationdate>= :Date0 and  ps_creationdate < (:Date0 + INTERVAL '1 days' * :Duration) and
    not exists (
        select * from
  (select distinct mt_tagid from post, message_tag, knows
        where
	k_person1id = :Person and
        k_person2id = m_ps_creatorid and
        mt_messageid = ps_postid and
        ps_creationdate < :Date0) tags
  where  tags.mt_tagid = recent.mt_tagid)
group by t_name
order by 2 desc, t_name
limit 10
