select top(20) p_personid, p_firstname, p_lastname, ct1, ct2, total AS totalcount
from
 ( select k_person2id
   from knows
   where
   k_person1id = 12345
   union
   select k2.k_person2id
   from knows k1, knows k2
   where
   k1.k_person1id = 12345 and k1.k_person2id = k2.k_person1id and k2.k_person2id <> 12345
 ) f, person, place p1, place p2,
 (
  select chn.m_creatorid, ct1, ct2, ct1 + ct2 as total
  from
   (
      select m_creatorid, count(*) as ct1 from message, place
      where
        m_locationid = pl_placeid and pl_name = 'Colombia' and
        m_creationdate >= 2010-06-01 and  m_creationdate < DATEADD(day, 28, 2010-06-01)
      group by m_creatorid
   ) chn,
   (
      select m_creatorid, count(*) as ct2 from message, place
      where
        m_locationid = pl_placeid and pl_name = 'Angola' and
        m_creationdate >= 2010-06-01 and  m_creationdate < DATEADD(day, 28, 2010-06-01)
      group by m_creatorid
   ) ind
  where CHN.m_creatorid = IND.m_creatorid
 ) cpc
where
f.k_person2id = p_personid and p_placeid = p1.pl_placeid and
p1.pl_containerplaceid = p2.pl_placeid and p2.pl_name <> 'Colombia' and p2.pl_name <> 'Angola' and
f.k_person2id = cpc.m_creatorid
order by totalcount desc, p_personid asc
;
