/* Q11. Friend triangles
\set country  '\'Belarus\''
\set startDate '\'2012-06-01T00:00:00.000+00:00\''::timestamp
 */
WITH persons_of_country_w_friends AS (
    SELECT p.p_personid AS personid
         , k.k_person2id AS friendid
         , k.k_creationdate AS k_creationdate
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
       AND co.pl_name = :country
)
SELECT count(*)
  FROM persons_of_country_w_friends p1
     , persons_of_country_w_friends p2
     , persons_of_country_w_friends p3
 WHERE 1=1
    -- join
   AND p1.friendid = p2.personid
   AND p2.friendid = p3.personid
   AND p3.friendid = p1.personid
    -- filter: unique trinagles only
   AND p1.personid < p2.personid
   AND p2.personid < p3.personid
    -- filter: only edges created after :startDate
   AND :startDate <= p1.k_creationdate
   AND :startDate <= p2.k_creationdate
   AND :startDate <= p3.k_creationdate
;
