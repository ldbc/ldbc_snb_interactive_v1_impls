/* Q25. Weighted interaction paths
\set person1Id 19791209303405
\set person2Id 19791209308983
\set startDate '\'2010-11-01T00:00:00.000+00:00\''::timestamp
\set endDate   '\'2010-12-01T00:00:00.000+00:00\''::timestamp
 */

WITH RECURSIVE reply_scores(r_threadid
                          , r_orig_personid
                          , r_reply_personid
                          , r_reply_messageid
                          , r_score
                           ) AS (
    SELECT p.m_messageid AS r_threadid
         , p.m_creatorid AS r_orig_personid
         , c.m_creatorid AS r_reply_personid
         , c.m_messageid AS r_reply_messageid
         , 1.0 AS r_score
      FROM forum f
         , message p
         , message c -- comment
     WHERE 1=1
        -- join
       AND f.f_forumid = p.m_ps_forumid
       AND p.m_messageid = c.m_c_replyof
        -- filter
       AND f.f_creationdate BETWEEN :startDate AND :endDate
       AND p.m_c_replyof IS NULL -- message, not comment
  UNION ALL
    SELECT r.r_threadid AS r_threadid
         , r.r_reply_personid AS r_orig_personid
         , c.m_creatorid AS r_reply_personid
         , c.m_messageid AS r_reply_messageid
         , 0.5 AS r_score
      FROM reply_scores r
         , message c
     WHERE 1=1
        -- join
       AND r.r_reply_messageid = c.m_c_replyof
)
   , person_pair_scores_directed AS (
    SELECT r_orig_personid AS orig_personid
         , r_reply_personid AS reply_personid
         , sum(r_score) AS score
      FROM reply_scores
     WHERE 1=1
        -- discard self replies from the score earned
       AND r_orig_personid != r_reply_personid
     GROUP BY r_orig_personid, r_reply_personid
)
   , person_pair_scores AS (
        -- note: this should already have both (A, B, score) and (B, A, score)
    SELECT coalesce(s1.orig_personid, s2.reply_personid) AS person1id
         , coalesce(s1.reply_personid, s2.orig_personid) AS person2id
         , coalesce(s1.score, 0.0) + coalesce(s2.score, 0.0) AS score
      FROM person_pair_scores_directed s1
           FULL OUTER JOIN person_pair_scores_directed s2
                        ON (s1.orig_personid = s2.reply_personid AND s1.reply_personid = s2.orig_personid)
)
   , wknows AS (
        -- weighted knows
    SELECT k_person1id
         , k_person2id
         , coalesce(score, 0.0) AS score
      FROM knows k
           LEFT JOIN person_pair_scores pps
                  ON (k.k_person1id = pps.person1id AND k.k_person2id = pps.person2id)
)
   , paths(startPerson
         , endPerson
         , path
         , weight
         , hopCount
         , person2Reached -- shows if person2 has been reached by any paths produced in the iteration that produced the path represented by this row
          ) AS (
    SELECT k_person1id AS startPerson
         , k_person2id AS endPerson
         , ARRAY[k_person1id, k_person2id]::bigint[] AS path
         , score AS weight
         , 1 AS hopCount
         , max(CASE WHEN k_person2id = :person2Id THEN 1 ELSE 0 END) OVER () as person2Reached
      FROM wknows
     WHERE 1=1
       AND k_person1id = :person1Id
  UNION ALL
    SELECT p.startPerson AS startPerson
         , k_person2id AS endPerson
         , array_append(path, k_person2id) AS path
         , weight + score AS weight
         , hopCount + 1 AS hopCount
         , max(CASE WHEN k_person2id = :person2Id THEN 1 ELSE 0 END) OVER () as person2Reached
      FROM paths p
         , wknows k
     WHERE 1=1
        -- join
       AND p.endPerson = k.k_person1id
       AND NOT p.path && ARRAY[k.k_person2id] -- person2id is not in the path
        -- stop condition
       AND p.person2Reached = 0
)
SELECT path, weight
  FROM paths
 WHERE 1=1
   AND endPerson = :person2Id
 ORDER BY weight DESC, path
;
