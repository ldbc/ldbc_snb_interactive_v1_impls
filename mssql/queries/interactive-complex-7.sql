/* Q7. Recent likers
\set personId 4398046511268
 */
select p_personid, p_firstname, p_lastname, l.l_creationdate, m_messageid,
    COALESCE(m_ps_imagefile, m_content),
    DATEDIFF_BIG(millisecond,'1970-01-01',l.l_creationdate - m_creationdate)/60 as minutesLatency,
    (case when exists (select 1 from knows where k_person1id = :personId and k_person2id = p_personid) then 0 else 1 end) as isnew
from
  (select TOP(20) l_personid, max(l_creationdate) as l_creationdate
   from likes, message
   where
     m_messageid = l_messageid and
     m_creatorid = :personId
   group by l_personid
   order by l_creationdate desc
  ) tmp, message, person, likes as l
where
    p_personid = tmp.l_personid and
    tmp.l_personid = l.l_personid and
    tmp.l_creationdate = l.l_creationdate and
    l.l_messageid = m_messageid
order by l_creationdate desc, p_personid asc
;
