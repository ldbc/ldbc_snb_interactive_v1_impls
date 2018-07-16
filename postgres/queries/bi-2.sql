/* Q2. Top tags for country, age, gender, time
\set startDate '\'2010-01-01T00:00:00.000+00:00\''::timestamp
\set endDate   '\'2010-11-08T00:00:00.000+00:00\''::timestamp
\set country1  '\'Ethiopia\''
\set country2  '\'Belarus\''
 */
SELECT co.pl_name AS "country.name"
     , extract(MONTH FROM p.ps_creationdate) as messageMonth
     , cr.p_gender AS "person.gender"
     , floor(extract(YEARS FROM age('2013-01-01'::date, cr.p_birthday))/5) AS ageGroup
     , t.t_name AS "tag.name"
     , count(*) AS messageCount
  FROM post p
     , message_tag pt
     , tag t
     , person cr -- creator
     , place  ci  -- city
     , place  co  -- country
 WHERE 1=1
    -- join
   AND p.ps_postid = pt.mt_messageid
   AND pt.mt_tagid = t.t_tagid
   AND p.ps_creatorid = cr.p_personid
   AND cr.p_placeid = ci.pl_placeid
   AND ci.pl_containerplaceid = co.pl_placeid
    -- filter
   AND co.pl_name in (:country1, :country2)
   AND p.ps_creationdate BETWEEN :startDate AND :endDate
 GROUP BY co.pl_name, messageMonth, cr.p_gender, t.t_name, ageGroup
HAVING count(*) > 100
 LIMIT 100
;
