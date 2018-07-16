/* Q18. How many persons have a given number of messages
\set date '\'2011-07-22T00:00:00.000+00:00\''::timestamp
\set lengthThreshold '20'
\set languages '\'{"ar", "hu"}\''::varchar[]
 */
WITH RECURSIVE post_all(psa_postid, psa_language, psa_creatorid, psa_posttype
                      , psa_content_isempty, psa_length, psa_creationday) AS (
    SELECT ps_postid, m_ps_language, ps_creatorid, 'Post'
         , ps_content IS NULL AS psa_content_isempty
         , ps_length
         , ps_creationdate --date_trunc('day', ps_creationdate) AS psa_creationday
      FROM post
     WHERE 1=1
       AND m_c_replyof IS NULL -- post, not comment
  UNION ALL
    SELECT ps_postid, psa.psa_language, ps_creatorid, 'Comment'
         , ps_content IS NULL AS psa_content_isempty
         , ps_length
         , ps_creationdate --date_trunc('day', ps_creationdate) AS psa_creationday
      FROM post p, post_all psa
     WHERE 1=1
       AND p.m_c_replyof = psa.psa_postid
)
, person_w_posts AS (
    SELECT p.p_personid, count(psa_postid) as messageCount
      FROM person p left join post_all psa on (
     1=1
       AND p.p_personid = psa.psa_creatorid
       AND NOT psa.psa_content_isempty
       AND psa_length < :lengthThreshold
       AND psa_creationday > :date
       AND psa_language = ANY(:languages)
       )
     GROUP BY p.p_personid
)
, message_count_distribution AS (
    SELECT pp.messageCount, count(*) as personCount
      FROM person_w_posts pp
     GROUP BY pp.messageCount
     ORDER BY personCount DESC, messageCount DESC
)
SELECT *
  FROM message_count_distribution
;
