select f_title, count(m_messageid)
from (
select f_title, f_forumid, f.k_person2id
from forum, forum_person,
 ( select k_person2id
   from knows
   where
   k_person1id = :personId
   union
   select k2.k_person2id
   from knows k1, knows k2
   where
   k1.k_person1id = :personId and k1.k_person2id = k2.k_person1id and k2.k_person2id <> :personId
 ) f
where f_forumid = fp_forumid and fp_personid = f.k_person2id and
      fp_creationdate >= :minDate
) tmp left outer join message
on tmp.f_forumid = m_ps_forumid and m_creatorid = tmp.k_person2id
group by f_forumid, f_title
order by 2 desc, f_forumid
limit 20
