select p_personid, p_firstname, p_lastname, ps_postid, COALESCE(ps_imagefile,'')||COALESCE(ps_content,''), ps_creationdate
from person, post, knows
where
    p_personid = ps_creatorid and
    ps_creationdate <= --2--::timestamp and
    k_person1id = --1-- and
    k_person2id = p_personid
order by ps_creationdate desc, p_personid asc
limit 20
