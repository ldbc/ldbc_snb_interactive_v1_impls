select p_personid,p_firstname, p_lastname, o_name, pc_workfrom --Q11
from person, person_company, organisation, place,
 ( select k_person2id
   from knows
   where
   k_person1id = --1--
   union
   select k2.k_person2id
   from knows k1, knows k2
   where
   k1.k_person1id = --1-- and k1.k_person2id = k2.k_person1id and k2.k_person2id <> --1--
 ) f
where
    p_personid = f.k_person2id and
    p_personid = pc_personid and
    pc_organisationid = o_organisationid and
    pc_workfrom < --2-- and
    o_placeid = pl_placeid and
    pl_name = '--3--'
order by pc_workfrom, p_personid, o_name desc
limit 10