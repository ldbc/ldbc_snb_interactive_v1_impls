select p_personid, p_firstname, p_lastname,
       ( select count(distinct ps_postid)
         from post, message_tag pt1
         where
         m_ps_creatorid = p_personid and ps_postid = mt_messageid and
         exists (select * from person_tag where pt_personid = :Person and pt_tagid = pt1.mt_tagid)
       ) -
       ( select count(*)
         from post
         where
         m_ps_creatorid = p_personid and
         not exists (select * from person_tag, message_tag where pt_personid = :Person and pt_tagid = mt_tagid and mt_messageid = ps_postid)
       ) as score,
       p_gender, pl_name
from person, place,
 ( select distinct k2.k_person2id
   from knows k1, knows k2
   where
   k1.k_person1id = :Person and k1.k_person2id = k2.k_person1id and k2.k_person2id <> :Person and
   not exists (select * from knows where k_person1id = :Person and k_person2id = k2.k_person2id)
 ) f
where
p_placeid = pl_placeid and
p_personid = f.k_person2id and
(
	(extract(month from p_birthday) = :HS0 and (case when extract(day from p_birthday) >= 21 then true else false end))
	or
	(extract(month from p_birthday) = :HS1 and (case when extract(day from p_birthday) <  22 then true else false end))
)
order by score desc, p_personid
limit 10
