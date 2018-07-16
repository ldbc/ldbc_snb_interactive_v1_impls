select p_personid, p_firstname, p_lastname, l.l_creationdate, ps_postid,
	COALESCE(m_ps_imagefile,'')||COALESCE(ps_content,''),
	EXTRACT(EPOCH FROM (l.l_creationdate - ps_creationdate)) / 60 as lag,
    (case when exists (select 1 from knows where k_person1id = :Person and k_person2id = p_personid) then 0 else 1 end) as isnew
from
  (select l_personid, max(l_creationdate) as l_creationdate
   from likes, post
   where
     ps_postid = l_messageid and
     ps_creatorid = :Person
   group by l_personid
   order by 2 desc
   limit 20
  ) tmp, post, person, likes as l
where
	p_personid = tmp.l_personid and
	tmp.l_personid = l.l_personid and
	tmp.l_creationdate = l.l_creationdate and
	l.l_messageid = ps_postid
order by 4 desc, 1
