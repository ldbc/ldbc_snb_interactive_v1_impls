/* Q1. Posting summary
\set date '\'2011-07-21T22:00:00.000+00:00\''::timestamp
 */
WITH 
  post_count AS (
    SELECT 0.0 + count(*) AS cnt
      FROM post
     WHERE 1=1
       AND ps_creationdate < :date
)
, post_prep AS (
    SELECT extract(year from ps_creationdate) AS messageYear
         , m_c_replyof IS NOT NULL AS isComment
         , CASE
             WHEN ps_length <  40 THEN 0 -- short
             WHEN ps_length <  80 THEN 1 -- one liner
             WHEN ps_length < 160 THEN 2 -- tweet
             ELSE                      3 -- long
           END AS lengthCategory
         , ps_length
      FROM post
     WHERE 1=1
       AND ps_creationdate < :date
       --AND ps_content IS NOT NULL
       AND m_ps_imagefile IS NULL -- FIXME CHECKME: posts w/ m_ps_imagefile IS NOT NULL should have ps_content IS NULL
)
SELECT messageYear, isComment, lengthCategory
     , count(*) AS messageCount
     , avg(ps_length) AS averageMessageLength
     , sum(ps_length) AS sumMessageLength
     , count(*) / pc.cnt AS percentageOfMessages
  FROM post_prep
     , post_count pc
 GROUP BY messageYear, isComment, lengthCategory, pc.cnt
 ORDER BY messageYear DESC, isComment ASC, lengthCategory ASC
;
