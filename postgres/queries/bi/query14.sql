/* Q14. Top thread initiators
\set startDate '\'2012-06-01T00:00:00.000+00:00\''::timestamp
\set endDate   '\'2012-07-01T00:00:00.000+00:00\''::timestamp
 */
WITH RECURSIVE post_all(psa_threadid
                      , psa_thread_creatorid
                      , psa_messageid
                      , psa_creationdate
                      , psa_messagetype
                       ) AS (
    SELECT ps_postid AS psa_threadid
         , ps_creatorid AS psa_thread_creatorid
         , ps_postid AS psa_messageid
         , ps_creationdate
         , 'Post'
      FROM post
     WHERE 1=1
       AND ps_replyof IS NULL -- post, not comment
       AND ps_creationdate BETWEEN :startDate AND :endDate
  UNION ALL
    SELECT psa.psa_threadid AS psa_threadid
         , psa.psa_thread_creatorid AS psa_thread_creatorid
         , ps_postid
         , ps_creationdate
         , 'Comment'
      FROM post p
         , post_all psa
     WHERE 1=1
       AND p.ps_replyof = psa.psa_messageid
)
SELECT p.p_personid AS "person.id"
     , p.p_firstname AS "person.firstName"
     , p.p_lastname AS "person.lastName"
     , count(DISTINCT psa.psa_threadid) AS threadCount
     -- if the thread initiator message does not count as a reply
     --, count(DISTINCT CASE WHEN psa.psa_messagetype = 'Comment' then psa.psa_messageid ELSE null END) AS messageCount
     , count(DISTINCT psa.psa_messageid) AS messageCount
  FROM person p left join post_all psa on (
       1=1
   AND p.p_personid = psa.psa_thread_creatorid
   AND psa_creationdate BETWEEN :startDate AND :endDate
   )
 GROUP BY p.p_personid, p.p_firstname, p.p_lastname
 ORDER BY messageCount DESC, p.p_personid
 LIMIT 100
;
