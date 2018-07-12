/* Q17. Friend triangles
\set country  '\'Belarus\''
 */
SELECT count(*)
  FROM (
    SELECT p.p_personid AS personid
         , k.k_person2id as friendid
      FROM person p
         , place ci -- city
         , place co -- country
         , knows k
     WHERE 1=1
        -- join
       AND p.p_placeid = ci.pl_placeid
       AND ci.pl_containerplaceid = co.pl_placeid
       AND p.p_personid = k.k_person1id
        -- filter
       AND co.pl_name = 'Belarus' -- FIXME:param :country
       ) p1
     , (
    SELECT p.p_personid AS personid
         , k.k_person2id as friendid
      FROM person p
         , place ci -- city
         , place co -- country
         , knows k
     WHERE 1=1
        -- join
       AND p.p_placeid = ci.pl_placeid
       AND ci.pl_containerplaceid = co.pl_placeid
       AND p.p_personid = k.k_person1id
        -- filter
       AND co.pl_name = 'Belarus' -- FIXME:param :country
       ) p2
     , (
    SELECT p.p_personid AS personid
         , k.k_person2id as friendid
      FROM person p
         , place ci -- city
         , place co -- country
         , knows k
     WHERE 1=1
        -- join
       AND p.p_placeid = ci.pl_placeid
       AND ci.pl_containerplaceid = co.pl_placeid
       AND p.p_personid = k.k_person1id
        -- filter
       AND co.pl_name = 'Belarus' -- FIXME:param :country
       ) p3
 WHERE 1=1
    -- join
   AND p1.friendid = p2.personid
   AND p2.friendid = p3.personid
   AND p3.friendid = p1.personid
    -- filter: unique trinagles only
   AND p1.personid < p2.personid
   AND p2.personid < p3.personid
;
