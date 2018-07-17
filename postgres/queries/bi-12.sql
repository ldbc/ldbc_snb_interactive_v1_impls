/* Q12. Trending Posts
\set date '\'2011-07-22T00:00:00.000+00:00\''::timestamp
\set likeThreshold 400
 */
SELECT m.m_messageid AS "message.id"
     , m.m_creationdate AS "message.creationDate"
     , c.p_firstname AS "creator.firstName"
     , c.p_lastname AS "creator.lastName"
     , count(*) as likeCount
  FROM message m
     , person c -- creator
     , likes l
 WHERE 1=1
    -- join
   AND m.m_creatorid = c.p_personid
   AND m.m_messageid = l.l_messageid
    -- filter
   AND m.m_creationdate > :date
 GROUP BY m.m_messageid
        , m.m_creationdate
        , c.p_firstname
        , c.p_lastname
HAVING count(*) > :likeThreshold
 ORDER BY likeCount DESC, m.m_messageid
 LIMIT 100
;
