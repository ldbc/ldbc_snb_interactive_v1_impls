/* Q3. Friends and friends of friends that have been to given countries
\set personId 6597069766734
\set countryXName '\'Angola\''
\set countryYName '\'Colombia\''
\set startDate '\'2010-06-01\''::date
\set durationDays 28
 */
select p_personid, p_firstname, p_lastname, ct1, ct2, total as totalcount
from
 ( select k_person2id
   from knows
   where
   k_person1id = :personId
   union
   select k2.k_person2id
   from knows k1, knows k2
   where
   k1.k_person1id = :personId and k1.k_person2id = k2.k_person1id and k2.k_person2id <> :personId
 ) f,  person, place p1, place p2,
 (
  select chn.m_c_creatorid, ct1, ct2, ct1 + ct2 as total
  from
   (
      select m_creatorid as m_c_creatorid, count(*) as ct1 from message, place
      where
        m_locationid = pl_placeid and pl_name = :countryXName and
        m_creationdate >= :startDate and  m_creationdate < :startDate
      group by m_c_creatorid
   ) chn,
   (
      select m_creatorid as m_c_creatorid, count(*) as ct2 from message, place
      where
        m_locationid = pl_placeid and pl_name = :countryYName and
        m_creationdate >= :startDate and  m_creationdate < :startDate
      group by m_creatorid --m_c_creatorid
   ) ind
  where CHN.m_c_creatorid = IND.m_c_creatorid
 ) cpc
where
f.k_person2id = p_personid and p_placeid = p1.pl_placeid and
p1.pl_containerplaceid = p2.pl_placeid and p2.pl_name <> :countryXName and p2.pl_name <> :countryYName and
f.k_person2id = cpc.m_c_creatorid
order by totalcount desc, p_personid asc
limit 20
;
