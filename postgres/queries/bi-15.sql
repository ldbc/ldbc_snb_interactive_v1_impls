/* Q15. Social normals
\set country  '\'Belarus\''
 */
WITH persons_of_country AS (
    SELECT p.p_personid AS personid
      FROM person p
         , place ci -- city
         , place co -- country
     WHERE 1=1
        -- join
       AND p.p_placeid = ci.pl_placeid
       AND ci.pl_containerplaceid = co.pl_placeid
        -- filter
       AND co.pl_name = :country
)
, persons_w_friendcount AS (
    SELECT p.personid
         , count(f.personid) AS friendCount
         , floor(avg(count(f.personid)) OVER ()) AS avgFriendCount
      FROM persons_of_country p -- persons to find
           LEFT JOIN (knows k INNER JOIN persons_of_country f -- friend
                                      ON (k.k_person2id = f.personid))
                  ON (p.personid = k.k_person1id)
     WHERE 1=1
        -- join given in the FROM clause
        -- filter
     GROUP BY p.personid
)
SELECT personid AS "person.id"
     , friendCount AS count
  FROM persons_w_friendcount
 WHERE 1=1
    -- filter
   AND friendCount = avgFriendCount
 ORDER BY personid
 LIMIT 100
;
