/* Q21. Zombies in a country
\set country '\'Belarus\''
\set endDate '\'2013-01-01T00:00:00.000+00:00\''::timestamp
 */
WITH zombies AS (
    SELECT p.p_personid AS zombieid
      FROM place co -- country
         , place ci -- city
         , person p
           LEFT JOIN post m ON (p.p_personid = m.ps_creatorid AND m.ps_creationdate BETWEEN p.p_creationdate AND :endDate)
     WHERE 1=1
        -- join
       AND co.pl_placeid = ci.pl_containerplaceid
       AND ci.pl_placeid = p.p_placeid
        -- filter
       AND co.pl_name = :country
       AND p.p_creationdate < :endDate
     GROUP BY p.p_personid
        -- average of [0, 1) messages per month is equivalent with having less messages than the month span between person creationDate and parameter :endDate
    HAVING count(ps_postid) < 12*extract(YEAR FROM :endDate)+extract(MONTH FROM :endDate) - (12*extract(YEAR FROM p.p_creationdate) + extract(MONTH FROM p.p_creationdate)) + 1
)
SELECT z.zombieid AS "zombie.id"
     , count(zl.zombieid) AS zombieLikeCount
     , count(l.l_personid) AS totalLikeCount
     , CASE WHEN count(l.l_personid) = 0 THEN 0 ELSE count(zl.zombieid)::float/count(l.l_personid) END AS zombieScore
  FROM post m
       INNER JOIN likes l ON (m.ps_postid = l.l_postid)
       INNER JOIN person p ON (l.l_personid = p.p_personid AND p.p_creationdate < :endDate)
       LEFT  JOIN zombies zl ON (p.p_personid = zl.zombieid) -- see if the like was given by a zombie
       RIGHT JOIN zombies z ON (z.zombieid = m.ps_creatorid)
 GROUP BY z.zombieid
 ORDER BY zombieScore DESC, z.zombieid
 LIMIT 100
;
