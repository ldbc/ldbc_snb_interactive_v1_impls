select p_personid, p_firstname, p_lastname,
       ps_postid, COALESCE(m_ps_imagefile,'')||COALESCE(ps_content,''), ps_creationdate
from
  ( select k_person2id
    from knows
    where
    k_person1id = :Person
    union
    select k2.k_person2id
    from knows k1, knows k2
    where
    k1.k_person1id = :Person and k1.k_person2id = k2.k_person1id and k2.k_person2id <> :Person
  ) f, person, post
where
  p_personid = ps_creatorid and p_personid = f.k_person2id and
  ps_creationdate < :Date0
order by ps_creationdate desc, ps_postid asc
limit 20
