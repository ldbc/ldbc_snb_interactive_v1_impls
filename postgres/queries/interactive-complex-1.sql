/* Q1. Transitive friends with certain name
\set personId 4398046511333
\set firstName '\'Jose\''
 */

select
  id,
  p_lastname,
  min (dist) as dist,
  p_birthday,
  p_creationdate,
  p_gender,
  p_browserused,
  p_locationip,
  (select array_agg(pe_email) from person_email where pe_personid = id group by pe_personid) as emails,
  (select array_agg(plang_language) from person_language where plang_personid = id group by plang_personid) as languages,
  p1.pl_name,
  (select array_agg(ARRAY[o2.o_name, pu_classyear::text, p2.pl_name]) from person_university, organisation o2, place p2  where pu_personid = id and pu_organisationid = o2.o_organisationid and o2.o_placeid = p2.pl_placeid group by pu_personid) as university,
       (select array_agg(ARRAY[o3.o_name, pc_workfrom::text, p3.pl_name]) from person_company, organisation o3, place p3 where pc_personid = id and pc_organisationid = o3.o_organisationid and o3.o_placeid = p3.pl_placeid group by pc_personid) as company
from
    (
    select k_person2id as id, 1 as dist from knows, person where k_person1id = :personId and p_personid = k_person2id and p_firstname = :firstName
    union all
    select b.k_person2id as id, 2 as dist from knows a, knows b, person
    where a.k_person1id = :personId
      and b.k_person1id = a.k_person2id
      and p_personid = b.k_person2id
      and p_firstname = :firstName
      and p_personid != :personId -- excluding start person
    union all
    select c.k_person2id as id, 3 as dist from knows a, knows b, knows c, person
    where a.k_person1id = :personId
      and b.k_person1id = a.k_person2id
      and b.k_person2id = c.k_person1id
      and p_personid = c.k_person2id
      and p_firstname = :firstName
      and p_personid != :personId -- excluding start person
    ) tmp, person, place p1
  where
	p_personid = id and
	p_placeid = p1.pl_placeid
  group by id, p_lastname, p_birthday, p_creationdate, p_gender, p_browserused, p_locationip, p1.pl_name
  order by dist, p_lastname, id LIMIT 20
